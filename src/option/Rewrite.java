package option;

import interpreter.Display;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Rewrite implements BrainfuckOption {

    /**
     * Display the program in its shorter version
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        if (language.getInst().size() == 0) language.setInst(program);
        Display.display(language.rewrite(os));
    }
}
