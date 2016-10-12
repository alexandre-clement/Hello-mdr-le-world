package option;

import language.Language;
import system.OperatingSystem;

/**
 * Brainfuck Project
 *
 * BfckMacro Interface
 *
 * Implement the Call method which contains the macro instructions
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public interface BrainfuckOption {
    Language language = new Language();
    OperatingSystem os = new OperatingSystem();

    /**
     * Execute the option on the program
     *
     * @param program the String version of the file
     */
    void Call(String program);
}
