package option;

import language.Instruction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * @author SmartCoding
 *         Created the 14 octobre 2016.
 */
public class Translate extends StdoutOption {
    private static final int SIDE = 3;

    @Override
    String getName() {
        return "--translate";
    }

    @Override
    public void Call(String filename, String program) {
        List<Instruction> inst = language.getRunningInstructions();
        if (inst.size() == 0) language.setRunningInstructions(program);
        int programSize = inst.size();
        int size = (int) Math.ceil(Math.sqrt(programSize)) * SIDE;
        int[] rbgArray = translate(inst, programSize, size);
        try {
            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, size, size, rbgArray, 0, size);
            ImageIO.write(image, "BMP", new File(filename + ".bmp"));
        } catch (java.io.IOException | IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }

    private int[] translate(List<Instruction> inst, int programSize, int size) {
        int[] rgbArray = new int[size * size];
        for (int i=0; i<programSize; i++) {
            putColor(rgbArray, inst.get(i).getColorCode(), (SIDE*i)%size, (SIDE*i)/size, size);
        }
        return rgbArray;
    }

    private void putColor(int[] rgbArray, Color color, int x, int y, int size) {
        for (int i=SIDE*y; i<SIDE*y+SIDE; i++) {
            for (int j=x; j<x+SIDE; j++) {
                rgbArray[i*size + j] = color.getRGB();
            }
        }
    }
}
