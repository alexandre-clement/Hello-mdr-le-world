package language.instruction;

import system.OperatingSystem;

/**
 * @author Alexandre Clement
 *         Created the 12 octobre 2016.
 */
public class Out implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.out();
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
}