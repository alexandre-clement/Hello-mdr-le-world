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
        if (!master.os.dp()) {
            master.os.bound(master.language.backTo(master.os.getI()));
        }
        master.os.nextI();
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
