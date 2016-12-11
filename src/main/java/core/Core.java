package core;

import exception.ExitException;
import exception.NotWellFormedException;
import instructions.*;
import interpreter.Flag;
import language.BitmapImage;
import probe.Metrics;
import probe.Probe;
import probe.Trace;

import java.io.IOException;
import java.util.Deque;

/**
 * @author Alexandre Clement
 * @author TANG Yi
 *         Created the 16/11/2016.
 */
public class Core
{
    private final String filename;

    public Core(String filename)
    {
        this.filename = filename;
    }

    public static Executable[] getExecutables()
    {
        return new Executable[]{new Increment(), new Decrement(), new Left(), new Right(), new Out(), new In(), new Jump(), new Back(), new JumpOptimised(), new BackOptimised()};
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

    /**
     * run the options of the user
     *
     * @param flags            options the user puts in
     * @param executionContext the execution context
     */
    public void run(Deque<Flag> flags, ExecutionContext executionContext) throws ExitException
    {
        do
        {
            switch (flags.pop())
            {
                case PRINT:
                    Probe probe = createProbe(flags, executionContext.getProgramLength());
                    print(executionContext, probe);
                    break;
                case REWRITE:
                    rewrite(executionContext);
                    break;
                case TRANSLATE:
                    translate(executionContext);
                    break;
                case CHECK:
                    check(executionContext);
                    break;
            }
        } while (!flags.isEmpty());
        executionContext.close();
    }

    /**
     * Créer une probe pour récupérer les métriques ou générer la trace lors de l'exécution du programme
     *
     * @param flags         les options présentes
     * @param programLength la taille du programme
     * @return une nouvelle probe initialiser
     */
    private Probe createProbe(Deque<Flag> flags, int programLength)
    {
        Probe createdProbe = new Probe();
        for (Flag flag : flags)
        {
            switch (flag)
            {
                case METRICS:
                    createdProbe.addMeter(new Metrics(programLength));
                    break;
                case TRACE:
                    createdProbe.addMeter(new Trace(filename));
                    break;
            }
        }
        return createdProbe;
    }

    /**
     * the '-p' option: execution of the program and print out the memory snapshot
     */
    private void print(ExecutionContext executionContext, Probe probe) throws ExitException
    {
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            executionContext.execute();
            probe.acknowledge(executionContext);
        }
        probe.getResult();
        standardOutput("\n" + executionContext.getMemorySnapshot());
    }

    /**
     * the '--rewrite' option: print out the short syntax of the program
     */
    private void rewrite(ExecutionContext executionContext)
    {
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
            standardOutput(executionContext.getCurrentInstruction().getShortcut());
        standardOutput('\n');
    }

    /**
     * the '--translate' option: translate the program to the color syntax and create a image file
     */
    private void translate(ExecutionContext executionContext)
    {

        int size = BitmapImage.SIZE * (int) Math.ceil(Math.sqrt(executionContext.getProgramLength()));
        int[] colorArray = new int[size * size];
        int div, mod;
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            div = (executionContext.getInstruction() * BitmapImage.SIZE) / size * BitmapImage.SIZE;
            for (int line = div * size; line < (div + BitmapImage.SIZE) * size; line += size)
            {
                mod = (executionContext.getInstruction() * BitmapImage.SIZE) % size;
                for (int column = mod; column < mod + BitmapImage.SIZE; column++)
                    colorArray[line + column] = executionContext.getCurrentInstruction().getColor().getRGB();
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
     *
     * @throws NotWellFormedException if the program is not well formed
     */
    private void check(ExecutionContext executionContext) throws NotWellFormedException
    {
        int close = 0;
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            if (close < 0)
                throw new NotWellFormedException();
            if (executionContext.getCurrentInstruction() == Instructions.JUMP)
                close += 1;
            else if (executionContext.getCurrentInstruction() == Instructions.BACK)
                close -= 1;
        }
        if (close != 0)
            throw new NotWellFormedException();
    }
}