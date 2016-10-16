package language.instruction;

import language.Instruction;
import option.BrainfuckOption;

import java.awt.*;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Incr implements Instruction {

    @Override
    public void exec(BrainfuckOption master) {
        BrainfuckOption.os.incr();
        BrainfuckOption.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "INCR";
    }

    @Override
    public Character getShortSyntax() {
        return '+';
    }

    @Override
    public Color getColorCode() {
        return new Color(255, 255, 255);
    }

    @Override
    public String toString() {
        return "Incr";
    }
}
