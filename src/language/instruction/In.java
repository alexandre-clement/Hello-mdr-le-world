package language.instruction;

import system.OperatingSystem;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class In implements Instruction{

    @Override
    public void exec(OperatingSystem os) {
        os.in();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "IN";
    }

    @Override
    public Character getShortSyntax() {
        return ',';
    }

    @Override
    public String toString() {
        return "In";
    }
}
