package language.instruction;

import language.Instruction;
import option.BrainfuckOption;

import java.awt.*;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Out implements Instruction {

    @Override
    public void exec(BrainfuckOption master) {
        BrainfuckOption.display.out(BrainfuckOption.os.out());
        BrainfuckOption.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "OUT";
    }

    @Override
    public Character getShortSyntax() {
        return '.';
    }

    @Override
    public Color getColorCode() {
        return new Color(0, 255, 0);
    }

    @Override
    public String toString() {
        return "Out";
    }
}
