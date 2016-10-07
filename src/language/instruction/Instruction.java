package language.instruction;

import model.Memory;
import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * Interface for instructions
 *
 * @author SmartCoding
 */
public interface Instruction {

    Memory memory = new Memory();
    /**
     * execute the instruction
     */
    void exec(OperatingSystem os);
}
