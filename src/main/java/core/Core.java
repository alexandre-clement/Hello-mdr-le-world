package core;

import exception.ExitException;
import exception.NotWellFormedException;
import instructions.*;
import interpreter.Flag;
import language.BitmapImage;
import main.Main;
import probe.Metrics;
import probe.Probe;
import probe.Time;
import probe.Trace;

import java.io.IOException;

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
     * run the options of the user
     *
     * @param flags            options the user puts in
     * @param probes the probes the user puts in
     * @param executionContext the execution context
     */
    public void run(Flag[] flags, Flag[] probes, ExecutionContext executionContext) throws ExitException
    {
        for (Flag flag : flags)
        {
            switch (flag)
            {
                case PRINT:
                    Probe probe = createProbe(probes, executionContext.getProgramLength());
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
        }
        executionContext.close();
    }

    /**
     * Créer une probe pour récupérer les métriques ou générer la trace lors de l'exécution du programme
     *
     * @param probes        les options présentes
     * @param programLength la taille du programme
     * @return une nouvelle probe initialiser
     */
    private Probe createProbe(Flag[] probes, int programLength)
    {
        Probe createdProbe = new Probe();
        for (Flag probe : probes)
        {
            switch (probe)
            {
                case METRICS:
                    createdProbe.addMeter(new Metrics(programLength));
                    break;
                case TRACE:
                    createdProbe.addMeter(new Trace(filename));
                    break;
                case TIME:
                    createdProbe.addMeter(new Time());
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
        probe.initialize();
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            executionContext.execute();
            probe.acknowledge(executionContext);
        }
        probe.getResult();
        Main.standardOutput("\n" + executionContext.getMemorySnapshot());
    }

    /**
     * the '--rewrite' option: print out the short syntax of the program
     */
    private void rewrite(ExecutionContext executionContext)
    {
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
            Main.standardOutput(executionContext.getCurrentInstruction().getShortcut());
        Main.standardOutput('\n');
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
        int length = Instructions.LoopType.values().length;
        int[] closes = new int[length];
        Instructions.LoopType loopType;

        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            for (int i = 0; i < length; i++)
            {
                if (closes[i] < 0)
                    throw new NotWellFormedException(executionContext.getInstruction());
            }
            loopType = executionContext.getCurrentInstruction().getLoopType();
            if (loopType != null && ((Loop) executionContext.getCurrentExecutable()).open())
                closes[loopType.ordinal()] += 1;
            else if (loopType != null && !((Loop) executionContext.getCurrentExecutable()).open())
                closes[loopType.ordinal()] -= 1;
        }

        for (int i = 0; i < length; i++)
        {
            if (closes[i] != 0)
                throw new NotWellFormedException();
        }
    }
}