package language.instruction;

import system.OperatingSystem;

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
    public String toString() {
        return "Left";
    }
}
