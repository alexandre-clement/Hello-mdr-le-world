package language.instruction;

import language.Instruction;
import option.BrainfuckOption;

import java.awt.*;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Left implements Instruction {

    @Override
    public void exec(BrainfuckOption master) {
        BrainfuckOption.os.left();
        BrainfuckOption.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "LEFT";
    }

    @Override
    public Character getShortSyntax() {
        return '<';
    }

    @Override
    public Color getColorCode() {
        return new Color(148, 0, 211);
    }

    @Override
    public String toString() {
        return "Left";
    }
}
