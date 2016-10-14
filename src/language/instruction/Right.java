package language.instruction;

import language.Instruction;
import option.BrainfuckOption;

import java.awt.*;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Right implements Instruction {

    @Override
    public void exec(BrainfuckOption master) {
        master.os.right();
        master.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "RIGHT";
    }

    @Override
    public Character getShortSyntax() {
        return '>';
    }

    @Override
    public Color getColorCode() {
        return new Color(0, 0, 255);
    }

    @Override
    public String toString() {
        return "Right";
    }
}
