package language.instruction;

import language.Instruction;
import language.Loop;
import option.BrainfuckOption;

import java.awt.*;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Back extends Loop implements Instruction {

    @Override
    public void exec(BrainfuckOption master) {
        if (!BrainfuckOption.os.dp()) {
            BrainfuckOption.os.bound(BrainfuckOption.language.backTo(BrainfuckOption.os.getI()));
        }
        BrainfuckOption.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "BACK";
    }

    @Override
    public Character getShortSyntax() {
        return ']';
    }

    @Override
    public Color getColorCode() {
        return new Color(255, 0, 0);
    }

    @Override
    public String toString() {
        return "Back";
    }
}
