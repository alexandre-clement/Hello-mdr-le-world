package language.instruction;

import language.Instruction;
import language.Language;
import language.*;
import model.OperatingSystem;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Back extends Loop implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        if (!os.dp()) {
            os.bound(language.backTo(os.getI()));
        }
        os.nextI();
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
    public String toString() {
        return "Back";
    }
}
