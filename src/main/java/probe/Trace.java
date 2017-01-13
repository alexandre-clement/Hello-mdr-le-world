package probe;

import core.ExecutionContext;

import java.io.*;

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
     * @throws IOException si le log rencontre une erreur
     */
    public Trace(String filename) throws IOException
    {
        step = 0;
        writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(filename + ".log"))));
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
        writer.format("%-22d%-25d%-25d%s%n", ++step, executionContext.getInstruction(), executionContext.getPointer(), executionContext.getMemorySnapshot());
    }
}
