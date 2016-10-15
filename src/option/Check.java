package option;

import interpreter.Display;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Check extends BrainfuckOption {

    @Override
    public String getName() {
        return "--check";
    }

    /**
     * Check if a program is well formed i.e if every jump instructions have a mirror back instruction
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String filename, String program) {
        if (language.getRunningInstructions().size() == 0) language.setRunningInstructions(program);
        if (!language.check(language.getRunningInstructions())) Display.exitCode(4);
    }
}
