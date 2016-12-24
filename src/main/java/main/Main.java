package main;

import core.Core;
import core.ExecutionContext;
import core.ExecutionContextBuilder;
import exception.ExitException;
import interpreter.Interpreter;
import language.Language;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 *         <p>
 *         Brainfuck Interpreter
 */
public class Main
{
    public static final double VERSION = 1.0;
    private static final Logger LOGGER = Logger.getLogger("Brainfuck - " + Calendar.getInstance().getTime().toString());

    private Main() {}

    public static void main(String... args)
    {
        int exit = 0;

        try
        {
            Interpreter interpreter = Interpreter.buildInterpreter(args);
            Language language = new Language(interpreter);
            Core core = new Core(language.getFilename());
            ExecutionContext context = new ExecutionContextBuilder()
                    .setIn(language.getIn())
                    .setOut(language.getOut())
                    .setExecutables(Core.getExecutables())
                    .buildFromFile(language.getFile());
            core.run(interpreter.getOptions(), interpreter.getProbes(), context);
        }
        catch (ExitException exception)
        {
            LOGGER.logp(Level.SEVERE, exception.getSourceClass(), exception.getSourceMethod(), exception.getMessage());
            LOGGER.throwing(exception.getSourceClass(), exception.getSourceMethod(), exception);
            exit = exception.getExit();
        }

        System.exit(exit);
    }

    /**
     * print out the parameter
     *
     * @param object to be print out
     */
    public static void standardOutput(Object object)
    {
        System.out.print(object);
    }
}
