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

    String getLongSyntax();

    Character getShortSyntax();
}
