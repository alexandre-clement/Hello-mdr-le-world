package language.instruction;

import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * Interface for instructions
 *
 * @author SmartCoding
 */
public interface Instruction {

    /**
     * execute the instruction
     */
    void exec(OperatingSystem os);
}
