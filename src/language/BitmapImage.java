package language;


import exception.LanguageException;

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
public class BitmapImage implements ReadFile
{

    public static final int SIZE = 3;
    private ImageInputStream stream;
    private ImageReadParam param;
    private Rectangle rectangle;
    private ImageReader reader;
    private int height;
    private int width;

    public static void createImage(String filename, int[] colorArray, int size) throws IOException
    {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, size, size, colorArray, 0, size);
        ImageIO.write(image, "BMP", new File(filename));
    }

    BitmapImage(String filename) throws IOException
    {
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
    public String next() throws IOException, LanguageException
    {
        if (rectangle.x == width && rectangle.y == height)
            return null;
        param.setSourceRegion(rectangle);

        BufferedImage image = reader.read(0, param);
        reader.reset();
        stream.seek(0);
        reader.setInput(stream);
        int[] rgbArray = image.getRGB(0, 0, SIZE, SIZE, null, 0, SIZE);
        if (Arrays.stream(rgbArray).boxed().collect(Collectors.toSet()).size() != 1)
            throw new LanguageException(127, "Bitmap not well formed at position " + rectangle.x / SIZE + ", " + rectangle.y / SIZE);
        if (rectangle.x < width - SIZE) rectangle.setLocation(rectangle.x + SIZE, rectangle.y);
        else if (rectangle.y < height - SIZE) rectangle.setLocation(0, rectangle.y + SIZE);
        else rectangle.setLocation(width, height);
        return String.valueOf(rgbArray[0]);
    }

    @Override
    public void close() throws IOException
    {
        reader.reset();
        try
        {
            stream.close();
        }
        catch (IOException exception)
        {
            if (!"closed".equals(exception.getMessage()))
                throw exception;
        }
    }
}