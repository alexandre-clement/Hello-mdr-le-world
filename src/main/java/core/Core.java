package core;

import exception.*;
import interpreter.Flag;
import language.BitmapImage;

import java.io.*;
import java.util.Deque;

/**
 * @author Alexandre Clement
 * @author TANG Yi
 *         Created the 16/11/2016.
 */
public class Core
{

    private final static int CAPACITY = 30000;
    public final static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    public final static int MIN = 0;
    
    private Probe probe;
    private String filename;
    private ExecutionContext executionContext;

    public Core(String filename)
    {
        this.filename = filename;
    }

    /**
     * run the options of the user
     * @param flags options the user puts in
     * @param executionContext the execution context
     */
    public void run(Deque<Flag> flags, ExecutionContext executionContext) throws ExitException
    {
        this.executionContext = executionContext;
        probe = new Probe();
        for (Flag flag : flags)
        {
            switch (flag)
            {
                case METRICS:
                    probe.addMeter(new Probe.Metrics(executionContext.program.length));
                    break;
                case TRACE:
                    probe.addMeter(new Probe.Trace(filename));
                    break;
            }
        }
        do {
            switch (flags.pop())
            {
                case PRINT:
                    print();
                    break;
                case REWRITE:
                    rewrite();
                    break;
                case TRANSLATE:
                    translate();
                    break;
                case CHECK:
                    check();
                    break;
            }
        } while (!flags.isEmpty());
    }

    /**
     * the '-p' option: execution of the program and print out the memory snapshot
     */
    private void print() throws LanguageException, CoreException
    {
        for (executionContext.instruction=0; executionContext.instruction<executionContext.program.length; executionContext.instruction++)
        {
            executionContext.program[executionContext.instruction].execute(executionContext);
            probe.acknowledge(executionContext);
        }
        probe.getResult();
        standardOutput("\n" + executionContext.getMemorySnapshot());
    }

    /**
     * the '--rewrite' option: print out the short syntax of the program
     */
    private void rewrite()
    {
        for (executionContext.instruction=0; executionContext.instruction<executionContext.program.length; executionContext.instruction++)
            standardOutput(executionContext.program[executionContext.instruction].getShortcut());
        standardOutput('\n');
    }

    /**
     * the '--translate' option: translate the program to the color syntax and create a image file
     */
    private void translate()
    {

        int size = BitmapImage.SIZE * (int) Math.ceil(Math.sqrt(executionContext.program.length));
        int[] colorArray = new int[size * size];
        int div, mod;
        for (executionContext.instruction=0; executionContext.instruction < executionContext.program.length; executionContext.instruction++)
        {
            div = (executionContext.instruction * BitmapImage.SIZE) / size * BitmapImage.SIZE;
            for (int line = div * size; line < (div + BitmapImage.SIZE) * size; line += size)
            {
                mod = (executionContext.instruction * BitmapImage.SIZE) % size;
                for (int column = mod; column < mod + BitmapImage.SIZE; column++)
                    colorArray[line + column] = executionContext.program[executionContext.instruction].getColor().getRGB();
            }
        }
        try
        {
            BitmapImage.createImage(filename + "_out.bmp", colorArray, size);
        }
        catch (IOException exception)
        {
            System.err.println("Error with translated image");
        }
    }

    /**
     * the '--check' option: check JUMP and BACK in the program are well formed
     * @throws NotWellFormedException if the program is not well formed
     */
    private void check() throws NotWellFormedException
    {
        int close = 0;
        for (Instructions instructions : executionContext.program)
        {
            if (close < 0)
                throw new NotWellFormedException();
            if (instructions == Instructions.JUMP)
                close += 1;
            else if (instructions == Instructions.BACK)
                close -= 1;
        }
        if (close != 0)
            throw new NotWellFormedException();
    }

    /**
     * print out the parameter
     * @param object to be print out
     */
    private static void standardOutput(Object object)
    {
        System.out.print(object);
    }
}