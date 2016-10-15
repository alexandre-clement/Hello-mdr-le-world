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
     */
    @Override
    public void Call(String filename, Object[] objects) {
        if (language.getRunningInstructions().size() == 0) language.setRunningInstructions(objects);
        if (!language.check(language.getRunningInstructions())) Display.exitCode(4);
    }
}
