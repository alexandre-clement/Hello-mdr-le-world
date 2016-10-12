package language.instruction;

import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Right implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.right();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "RIGHT";
    }

    @Override
    public Character getShortSyntax() {
        return '>';
    }

    @Override
    public String toString() {
        return "Right";
    }
}
