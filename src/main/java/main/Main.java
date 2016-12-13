package main;

import core.Core;
import exception.ExitException;
import interpreter.Interpreter;
import language.Language;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
public class Main
{
    public final static double VERSION = 1.0;

    public static void main(String... args)
    {
        int exit = 0;

        try
        {
            Interpreter interpreter = Interpreter.buildInterpreter(args);
            if (interpreter == null)
                System.exit(exit);
            Language language = new Language(interpreter);
            Core core = new Core(language.getFilename());
            core.run(interpreter.getOptions(), language.getExecutionContext());
        }
        catch (ExitException exception)
        {
            System.out.flush();
            System.err.println(exception.getMessage());
            exit = exception.getExit();
        }

        System.exit(exit);
    }
}
