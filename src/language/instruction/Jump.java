package language.instruction;

import language.Instruction;
import language.Loop;
import option.BrainfuckOption;

import java.awt.*;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Jump extends Loop implements Instruction {

    public Jump() {
        super();
    }
    @Override
    public void exec(BrainfuckOption master) {
        if (master.os.dp()) {
            master.os.bound(master.language.jumpTo(master.os.getI()));
        }
        master.os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "JUMP";
    }

    @Override
    public Character getShortSyntax() {
        return '[';
    }

    @Override
    public Color getColorCode() {
        return new Color(255, 127, 0);
    }

    @Override
    public String toString() {
        return "Jump";
    }
}
