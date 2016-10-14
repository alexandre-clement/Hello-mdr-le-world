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

public abstract class BrainfuckOption {
    public Input input;
    public Display display;
    public Language language = new Language();
    public OperatingSystem os = new OperatingSystem();

    /**
     * @return the name of the option
     */
    abstract String getName();

    /**
     * Check if the option is in the commandline
     * @param commandline with the option and the file
     * @return true if the option is in the commandline, false otherwise
     */
    public boolean isIn(String... commandline) {
        for (String command: commandline) {
            if (getName().equals(command)) return true;
        }
        return false;
    }

    /**
     * Execute the option on the program
     *
     * @param program the String version of the file
     */
    public abstract void Call(String program);

    public void setInput(Input input) {
        this.input = input;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}
