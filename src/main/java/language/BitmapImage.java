package language;


import exception.ExitException;

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
 * Lit et écrit des images bitmap
 *
 * @author Alexandre Clement
 * @see ReadFile
 * @since 17/11/2016.
 */
public class BitmapImage implements ReadFile
{
    /**
     * Côtés d'une instruction dans une image
     */
    public static final int SIZE = 3;
    private final ImageInputStream stream;
    private final ImageReadParam param;
    private final Rectangle rectangle;
    private final ImageReader reader;
    private final int height;
    private final int width;

    /**
     * @param filename le nom de l'image
     * @throws IOException si l'image n'est pas trouvée
     */
    BitmapImage(String filename) throws IOException
    {
        stream = ImageIO.createImageInputStream(new File(filename));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
        if (!readers.hasNext())
            throw new FileNotFoundException();
        reader = readers.next();
        reader.setInput(stream);
        param = reader.getDefaultReadParam();
        height = reader.getHeight(0);
        width = reader.getWidth(0);
        if (height % 3 != 0 || width % 3 != 0)
            throw new IOException();
        rectangle = new Rectangle(0, 0, SIZE, SIZE);
    }

    /**
     * Créer une image à partie d'un tableau des valeurs
     *
     * @param filename   le nom de l'image
     * @param colorArray les valeurs à insérer dans l'image
     * @param size       la taille de l'image
     */
    public static void createImage(String filename, int[] colorArray, int size) throws IOException
    {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, size, size, colorArray, 0, size);
        ImageIO.write(image, "BMP", new File(filename));
    }

    /**
     * @return la valeur du prochain carré de côté SIZE
     * @throws ExitException si le carré n'est pas conforme i.e les SIZExSIZE pixels ne sont pas de la même couleur
     */
    @Override
    public String next() throws ExitException
    {
        if (rectangle.x == width && rectangle.y == height)
            return null;
        param.setSourceRegion(rectangle);
        try
        {
            BufferedImage image = reader.read(0, param);
            reader.reset();
            stream.seek(0);
            reader.setInput(stream);
            int[] rgbArray = image.getRGB(0, 0, SIZE, SIZE, null, 0, SIZE);
            if (Arrays.stream(rgbArray).boxed().collect(Collectors.toSet()).size() != 1)
                throw new ExitException(127, this.getClass().getSimpleName(), "#next", "Bitmap not well formed at position " + rectangle.x / SIZE + ", " + rectangle.y / SIZE);
            if (rectangle.x < width - SIZE)
                rectangle.setLocation(rectangle.x + SIZE, rectangle.y);
            else if (rectangle.y < height - SIZE)
                rectangle.setLocation(0, rectangle.y + SIZE);
            else rectangle.setLocation(width, height);
            return String.valueOf(Math.abs(rgbArray[0]));
        }
        catch (IOException exception)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#next", exception);
        }
    }

    /**
     * Ferme l'image
     */
    @Override
    public void close() throws ExitException
    {
        reader.reset();
        try
        {
            stream.close();
        }
        catch (IOException exception)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#close", exception);
        }
    }
}