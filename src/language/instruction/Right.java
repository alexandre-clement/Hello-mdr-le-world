package language.instruction;

import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * @author TANG Yi
 */
public class Right implements Instruction {
    @Override
    public void exec(OperatingSystem os) {
        os.right();
        os.nextI();
    }
}
