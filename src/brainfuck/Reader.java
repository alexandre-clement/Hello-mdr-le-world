package brainfuck;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
class Reader {
    private FileType fileType;

    Reader(String filename) throws IOException, NoSuchElementException {
        String type = filename.substring(filename.lastIndexOf('.'));
        fileType = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(type)).findFirst().get();
        fileType.setSource(filename);
    }
    boolean hasNext() {
        return fileType.hasNext();
    }

    String next() throws IOException {
        return fileType.next();
    }

    private interface Read {
        void setSource(String filename) throws IOException;
        boolean hasNext();
        String next() throws IOException;
    }

    private enum FileType implements Read {

        Bf(".bf") {
            private Scanner source;
            @Override
            public void setSource(String filename) throws IOException{
                this.source = new Scanner(new File(filename));
            }
            @Override
            public boolean hasNext() {
                return source.hasNextLine();
            }
            @Override
            public String next() {
                return source.nextLine();
            }
        },
        Bmp(".bmp") {
            private final int size = 3;
            private String filename;
            private ImageReadParam param;
            private Rectangle rectangle;
            private int height;
            private int width;
            @Override
            public void setSource(String filename) throws IOException {
                this.filename = filename;
                ImageInputStream stream = ImageIO.createImageInputStream(new File(filename));
                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
                if (!readers.hasNext()) throw new FileNotFoundException();
                ImageReader reader = readers.next();
                reader.setInput(stream);
                param = reader.getDefaultReadParam();
                height = reader.getHeight(0);
                width = reader.getWidth(0);
                if (height % 3 != 0 || width % 3 != 0) throw new IOException();
                rectangle = new Rectangle(0, 0, size, size);
            }
            @Override
            public boolean hasNext() {
                return rectangle.x < width || rectangle.y < height;
            }
            @Override
            public String next() throws IOException {
                param.setSourceRegion(rectangle);
                if (rectangle.x < width - size) rectangle.setLocation(rectangle.x + size, rectangle.y);
                else if (rectangle.y < height - size) rectangle.setLocation(0, rectangle.y + size);
                else rectangle.setLocation(width, height);
                ImageInputStream stream = ImageIO.createImageInputStream(new File(filename));
                Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
                if (!readers.hasNext()) throw new FileNotFoundException();
                ImageReader reader = readers.next();
                reader.setInput(stream);
                BufferedImage image = reader.read(0, param);
                int[] rgbArray = image.getRGB(0, 0, size, size, null, 0, size);
                if (Arrays.stream(rgbArray).boxed().collect(Collectors.toSet()).size() == 1) return String.valueOf(rgbArray[0]);
                else throw new IOException();
            }
        };

        private String extension;

        private String getExtension() { return extension; }

        FileType(String extension) {
            this.extension = extension;
        }
    }
}
