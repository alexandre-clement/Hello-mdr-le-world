package main;

import Language.Language;
import core.Core;
import exception.ExitException;
import interpreter.Flag;
import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 * speed test: 77200 ms
 */
public class Main {
    public final static double VERSION = 1.0;

    public static void main(String[] args) {
        int exit = 0;

        try {
            Interpreter interpreter = Interpreter.buildInterpreter(args);
            if (interpreter == null)
                System.exit(exit);
            Language language = new Language(interpreter);
            Core core = new Core(language);
            core.run();
        } catch (ExitException exception) {
            System.err.println(exception.getMessage());
            exit = exception.getExit();
        }

        System.exit(exit);
    }
}
