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

public interface BfckOption {
    Language language = new Language();
    OperatingSystem os = new OperatingSystem();

    void Call(String program);
}
