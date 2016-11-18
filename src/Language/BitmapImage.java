package Language;


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
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public class BitmapImage implements ReadFile {

    private static final int SIZE = 3;
    private ImageInputStream stream;
    private ImageReadParam param;
    private Rectangle rectangle;
    private ImageReader reader;
    private BufferedImage image;
    private int height;
    private int width;

    public BitmapImage(String filename) throws IOException {
        stream = ImageIO.createImageInputStream(new File(filename));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
        if (!readers.hasNext()) throw new FileNotFoundException();
        reader = readers.next();
        reader.setInput(stream);
        param = reader.getDefaultReadParam();
        height = reader.getHeight(0);
        width = reader.getWidth(0);
        if (height % 3 != 0 || width % 3 != 0) throw new IOException();
        rectangle = new Rectangle(0, 0, SIZE, SIZE);
    }

    @Override
    public String next() throws IOException {
        if (rectangle.x == width && rectangle.y == height)
            return null;
        param.setSourceRegion(rectangle);
        if (rectangle.x < width - SIZE) rectangle.setLocation(rectangle.x + SIZE, rectangle.y);
        else if (rectangle.y < height - SIZE) rectangle.setLocation(0, rectangle.y + SIZE);
        else rectangle.setLocation(width, height);
        image = reader.read(0, param);
        reader.reset();
        stream.seek(0);
        reader.setInput(stream);
        int[] rgbArray = image.getRGB(0, 0, SIZE, SIZE, null, 0, SIZE);
        if (Arrays.stream(rgbArray).boxed().collect(Collectors.toSet()).size() == 1) return String.valueOf(rgbArray[0]);
        else throw new IOException();
    }

    @Override
    public void close() throws IOException {
        reader.reset();
        try {
            stream.close();
        }catch (IOException exception) {
            if (!"closed".equals(exception.getMessage()))
                throw exception;
        }
    }
}