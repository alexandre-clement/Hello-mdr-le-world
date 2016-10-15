package language;

import option.BrainfuckOption;

import java.awt.*;

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
    void exec(BrainfuckOption master);

    /**
     * @return the long syntax of the instruction
     */
    String getLongSyntax();

    /**
     * @return the short syntax of the instruction
     */
    Character getShortSyntax();

    /**
     * @return the color of the instruction
     */
    Color getColorCode();

}
