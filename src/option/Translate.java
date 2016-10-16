package option;

import file.Bmp;
import language.Instruction;

import java.awt.*;
import java.util.List;

/**
 * @author SmartCoding
 *         Created the 14 octobre 2016.
 */
public class Translate extends StdoutOption {


    @Override
    public String getName() {
        return "--translate";
    }

    @Override
    public void Call(String filename, Object[] objects) {
        if (language.getRunningInstructions().size() == 0) language.setRunningInstructions(objects); // avoid reset the instructions
        List<Instruction> inst = language.getRunningInstructions();
        int programSize = inst.size();
        int size = (int) Math.ceil(Math.sqrt(programSize)) * Bmp.SIDE;
        int[] rbgArray = translate(inst, programSize, size);
        Bmp.write(filename, rbgArray, size);
    }

    private int[] translate(List<Instruction> inst, int programSize, int size) {
        int[] rgbArray = new int[size * size];
        for (int i=0; i<programSize; i++) {
            putColor(rgbArray, inst.get(i).getColorCode(), (Bmp.SIDE*i)%size, (Bmp.SIDE*i)/size, size);
        }
        return rgbArray;
    }

    private void putColor(int[] rgbArray, Color color, int x, int y, int size) {
        for (int i=Bmp.SIDE*y; i<Bmp.SIDE*y+Bmp.SIDE; i++) {
            for (int j=x; j<x+Bmp.SIDE; j++) {
                rgbArray[i*size + j] = color.getRGB();
            }
        }
    }
}
