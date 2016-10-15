package file;

import interpreter.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Brainfuck Project
 * Bmp Class
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Bmp extends BrainfuckFile {
    public static final int SIDE = 3;

    @Override
    public Object[] ReadFile() {
        try {
            BufferedImage image = ImageIO.read(super.getFile());
            int width = image.getWidth();
            int height = image.getHeight();
            if (width % SIDE != 0 || height%SIDE!=0 || width != height) return new Color[0];
            Color[] colors = new Color[width * height / SIDE / SIDE];
            for (int y=0; y<height; y+=SIDE) {
                for (int x=0; x<width; x+=SIDE) {
                    colors[(y * width / SIDE + x) / SIDE] = getColor(image, x, y);
                }
            }
            return colors;
        } catch (IOException exception) {
            Display.display(exception);
        }
        return new Color[0];
    }

    private Color getColor(BufferedImage image, int x, int y) {
        int[] rgbArray = image.getRGB(x, y, SIDE, SIDE, null, 0, SIDE);
        if (Arrays.stream(rgbArray).boxed().collect(Collectors.toSet()).size() == 1) return new Color(rgbArray[0]);
        return null;
    }

    public static void write(String filename, int[] rbgArray, int size) {
        try {
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, size, size, rbgArray, 0, size);
            ImageIO.write(image, "BMP", new File(filename + ".bmp"));
        } catch (java.io.IOException | IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }
}
