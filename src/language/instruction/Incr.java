package language.instruction;

import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Incr implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.incr();
        os.nextI();
    }
}
