package probe;

import core.ExecutionContext;
import exception.ExitException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create the trace of the file in a p.log file.
 *
 * @author Alexandre Clement
 * @see Meter
 * @since 08/12/2016.
 */
public class Trace implements Meter
{
    private long step;
    private PrintWriter writer;

    /**
     * Creer la trace de l'execution.
     *
     * @param filename le nom du fichier
     * @throws ExitException si le log rencontre une erreur
     */
    public Trace(String filename) throws ExitException
    {
        step = 0;
        try
        {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(filename + ".log")));
        }
        catch (IOException e)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#Trace", e);
        }
    }

    /**
     * Initialise le fichier en inscrivant le noms des colonnes.
     */
    @Override
    public void initialize()
    {
        writer.format("%14s%25s%20s%28s%n%n", "Execution step", "Execution pointer", "Data pointer", "Memory snapshot");
    }

    /**
     * Close the logfile.
     */
    @Override
    public void getResult()
    {
        writer.close();
    }

    /**
     * Add the context in log.
     *
     * @param executionContext the current execution context of the program
     */
    @Override
    public void acknowledge(ExecutionContext executionContext)
    {
        writer.format("%7d%25d%22d                  %s%n", ++step, executionContext.getInstruction(), executionContext.getPointer(), executionContext.getMemorySnapshot());
    }
}
