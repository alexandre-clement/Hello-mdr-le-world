package language.instruction;

import model.OperatingSystem;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Left implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.left();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "LEFT";
    }

    @Override
    public Character getShortSyntax() {
        return '<';
    }

    @Override
    public String toString() {
        return "Left";
    }
}
