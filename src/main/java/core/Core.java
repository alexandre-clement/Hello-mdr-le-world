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
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Execute les options sur le programme.
 *
 * @author Alexandre Clement
 * @author TANG Yi
 * @see ExecutionContext
 * @see Instructions
 * @see Executable
 * @see Probe
 * @see Flag
 * @since 16/11/2016.
 */
public class Core
{
    /**
     * Le nom du fichier.
     */
    private final String filename;

    private PrintStream out;

    /**
     * @param filename Le nom du fichier a execute
     */
    public Core(String filename)
    {
        this.filename = filename;
        out = Main.DEFAULT_OUT;
    }

    /**
     * @return Les instructions executables disponibles
     */
    public static Executable[] getExecutables()
    {
        return new Executable[]{new Increment(), new Decrement(), new Left(), new Right(), new Out(), new In(), new Jump(), new Back(), new JumpOptimised(), new BackOptimised()};
    }

    public void setOut(PrintStream out)
    {
        this.out = out;
    }

    /**
     * Execute les options sur le context.
     *
     * @param flags            les options a execute
     * @param probes           les metriques
     * @param executionContext le contexte d'execution
     * @throws ExitException si l'execution du fichier engendre une erreur
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
                default:
            }
        }
        executionContext.close();
    }

    /**
     * Creer une probe pour recuperer les metriques ou generer la trace lors de l'execution du programme.
     *
     * @param probes        les options presentes
     * @param programLength la taille du programme
     * @return une nouvelle probe initialiser
     * @throws ExitException si le fichier log ne peut pas etre creer
     */
    private Probe createProbe(Flag[] probes, int programLength) throws ExitException
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
                    addTrace(createdProbe);
                    break;
                case TIME:
                    createdProbe.addMeter(new Time());
                    break;
                default:
                    throw new UnsupportedOperationException("Probe \"" + probe + "\" not implemented yet");
            }
        }
        return createdProbe;
    }

    private void addTrace(Probe createdProbe) throws ExitException
    {
        try
        {
            createdProbe.addMeter(new Trace(filename));
        }
        catch (IOException e)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#Trace", e);
        }
    }

    /**
     * L'option p: execute le programme avec les metriques et affiche l'etat de la memoire.
     *
     * @param executionContext le contexte d'execution sur lequel le programme va s'executer
     * @param probe            les metriques a utiliser lors de l'execution du programme
     * @throws ExitException si l'execution du programme engendre une erreur
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
        out.print("\n" + executionContext.getMemorySnapshot() + "\n");
    }

    /**
     * L'option rewrite: affiche le programme en syntaxe courte.
     *
     * @param executionContext le contexte contenant le programme a retranscrire en syntaxe courte
     */
    private void rewrite(ExecutionContext executionContext)
    {
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
            out.print(executionContext.getCurrentInstruction().getShortcut());
        out.print('\n');
    }

    /**
     * L'option translate: retranscrit le programme en image bitmap.
     *
     * @param executionContext le contexte contenant le programme a retranscrire en image
     * @throws ExitException si l'ecriture de l'image engendre une erreur
     */
    private void translate(ExecutionContext executionContext) throws ExitException
    {

        int size = BitmapImage.SIZE * (int) Math.ceil(Math.sqrt(executionContext.getProgramLength()));
        int[] colorArray = new int[size * size];
        int div;
        int mod;
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
            throw new ExitException(3, this.getClass().getSimpleName(), "#translate", exception);
        }
    }

    /**
     * L'option check: verifie si le programme est bien forme.
     * <p>
     * i.e toutes les boucles ouvertes sont fermees et vice-versa
     *
     * @param executionContext le contexte qui doit etre verifier
     * @throws NotWellFormedException si le programme n'est pas bien forme
     */
    private void check(ExecutionContext executionContext) throws NotWellFormedException
    {
        int length = Instructions.LoopType.values().length;
        List<Deque<Integer>> closes = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
        {
            closes.add(new ArrayDeque<>());
        }

        Instructions.LoopType loopType;

        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
        {
            loopType = executionContext.getCurrentInstruction().getLoopType();
            if (loopType != null && ((Loop) executionContext.getCurrentExecutable()).open())
            {
                closes.get(loopType.ordinal()).push(executionContext.getInstruction());
            }
            else if (loopType != null && !((Loop) executionContext.getCurrentExecutable()).open())
            {
                if (closes.get(loopType.ordinal()).isEmpty())
                    throw new NotWellFormedException(this.getClass().getSimpleName(), "#check", executionContext.getInstruction());
                closes.get(loopType.ordinal()).pop();
            }
        }

        for (Deque<Integer> close : closes)
        {
            if (!close.isEmpty())
                throw new NotWellFormedException(this.getClass().getSimpleName(), "#check", close.pop());
        }
    }
}