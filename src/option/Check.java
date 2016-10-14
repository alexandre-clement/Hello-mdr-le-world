package option;

import interpreter.Display;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Check implements BrainfuckOption {

    /**
     * Check if a program is well formed i.e if every jump instructions have a mirror back instruction
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        if (language.getInst().size() == 0) language.setInst(program);
        if (!language.check(language.getInst())) Display.exitCode(4);
    }
}
