package option;

import interpreter.Display;
import interpreter.Input;
import language.Language;
import model.OperatingSystem;

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
    Input input = new Input();
    Display display = new Display();
    Language language = new Language();
    OperatingSystem os = new OperatingSystem();

    /**
     * Execute the option on the program
     * @param filename the name of the file
     * @param objects an array with the content of the file
     *                instance depends on type of the file
     */
    void Call(String filename, Object[] objects);
}
