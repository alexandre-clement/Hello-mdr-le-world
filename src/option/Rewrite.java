package option;

import interpreter.Display;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Rewrite extends StdoutOption {

    @Override
    public String getName() {
        return "--rewrite";
    }

    /**
     * Display the program in its shorter version
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        if (language.getInst().size() == 0) language.setInst(program);
        // System.out.println(language);
        Display.display(language.rewrite(), '\n');
    }
}
