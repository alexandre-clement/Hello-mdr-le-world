package option;

import interpreter.Display;
import interpreter.Input;
import interpreter.Interpreter;

import java.util.stream.Collectors;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
abstract class FileOption extends BrainfuckOption {
    private String filename = null;

    /**
     * Check if the file option is in the command line
     * if it is, set the filename with the next command
     * replace the next command by null, avoiding problem when we will search the program in the command line
     * @param commandline with the option and the file
     * @return true the file option is in the command line, false otherwise
     */
    @Override
    public boolean isIn(String... commandline) {
        for (int i=0; i < commandline.length; i++) {
            if (getName().equals(commandline[i])) {
                //if the next String exists and isn't an option
                if (i+1 < commandline.length && !Interpreter.options.stream()
                        .map(BrainfuckOption::getName).collect(Collectors.toList()).contains(commandline[i+1])) {
                    filename = commandline[i + 1]; //set the filename
                    commandline[i + 1] = null; //set the String as null, to avoid disturbing finding the executable file
                    return true;
                }else {
                    Display.exitCode(3);
                }
            }
        }
        return false;
    }

    String getFilename() {
        return filename;
    }
}

