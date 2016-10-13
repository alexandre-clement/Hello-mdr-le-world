package language;

import model.OperatingSystem;

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
    void exec(OperatingSystem os, Language language);

    /**
     * @return the long syntax of the instruction
     */
    String getLongSyntax();

    /**
     * @return the short syntax of the instruction
     */
    Character getShortSyntax();
}
