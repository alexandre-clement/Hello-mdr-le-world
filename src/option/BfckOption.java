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
 *
 * @author SmartCoding
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public interface BfckOption {
    Language language = new Language();
    void Call(String program, OperatingSystem os);
}
