package option;

import language.Instruction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void Call(String program) {
        List<Instruction> inst = language.getInst();
        if (inst.size() == 0) language.setInst(program);
        translate(inst);
    }

    private List<Color[]> translate(List<Instruction> inst) {
        List<Color[]> colors = new ArrayList<>();
        for (Instruction instruction: inst) {
            Color[] color = new Color[SIDE * SIDE];
            Arrays.fill(color, instruction.getColorCode());
            colors.add(color);
        }
        return colors;
    }
}
