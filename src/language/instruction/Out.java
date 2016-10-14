package language.instruction;

import interpreter.Display;
import language.Instruction;
import language.Language;
import model.OperatingSystem;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Out implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        Display.display(outASCII(os));
        os.nextI();
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
    public String toString() {
        return "Out";
    }

    private char outASCII(OperatingSystem os) {
        return (char)os.out();
    }
}
