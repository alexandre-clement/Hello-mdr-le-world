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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Exécute les options sur le programme
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
     * Le nom du fichier
     */
    private final String filename;

    /**
     * @param filename Le nom du fichier a exécuté
     */
    public Core(String filename)
    {
        this.filename = filename;
    }

    /**
     * @return Les instructions exécutables disponibles
     */
    public static Executable[] getExecutables()
    {
        return new Executable[]{new Increment(), new Decrement(), new Left(), new Right(), new Out(), new In(), new Jump(), new Back(), new JumpOptimised(), new BackOptimised()};
    }

    /**
     * Exécute les options sur le context
     *
     * @param flags            les options a exécuté
     * @param probes           les métriques
     * @param executionContext le contexte d'exécution
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
     * Créer une probe pour récupérer les métriques ou générer la trace lors de l'exécution du programme
     *
     * @param probes        les options présentes
     * @param programLength la taille du programme
     * @return une nouvelle probe initialiser
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
                    createdProbe.addMeter(new Trace(filename));
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

    /**
     * L'option p: exécute le programme avec les métriques et affiche l'état de la mémoire
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
     * L'option rewrite: affiche le programme en syntaxe courte
     */
    private void rewrite(ExecutionContext executionContext)
    {
        for (; executionContext.hasNextInstruction(); executionContext.nextInstruction())
            Main.standardOutput(executionContext.getCurrentInstruction().getShortcut());
        Main.standardOutput('\n');
    }

    /**
     * L'option translate: retranscrit le programme en image bitmap
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
     * L'option check: vérifie si le programme est bien formé
     * i.e toutes les boucles ouvertes sont fermées et vice-versa
     *
     * @throws NotWellFormedException si le programme n'est pas bien formé
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