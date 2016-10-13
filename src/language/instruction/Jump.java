package language.instruction;

import language.Instruction;
import language.Language;
import language.Loop;
import model.OperatingSystem;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Jump extends Loop implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        if (os.dp()) {
            os.bound(language.jumpTo(os.getI()));
        }
        os.nextI();
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
    public String toString() {
        return "Jump";
    }
}
