package language.instruction;

import model.Memory;
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
    }
}
