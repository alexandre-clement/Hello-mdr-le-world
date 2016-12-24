package probe;

import core.ExecutionContext;
import main.Main;

/**
 * @author Alexandre Clement
 *         Created the 08/12/2016.
 *         <p>
 *         Calculate the metrics of the program and print them out using the method getResult
 */
public class Metrics implements Meter
{
    private long start;
    private long execMove;
    private long dataMove;
    private long dataWrite;
    private long dataRead;
    private long length;

    /**
     * @param length la taille du programme
     */
    public Metrics(long length)
    {
        this.length = length;
    }

    /**
     * Initialise toutes les valeurs
     */
    @Override
    public void initialize()
    {
        start = System.currentTimeMillis();
        execMove = 0;
        dataMove = 0;
        dataWrite = 0;
        dataRead = 0;
    }

    /**
     * Print out the metrics
     */
    @Override
    public void getResult()
    {
        String metrics = "\nPROG_SIZE: " + length + '\n';
        metrics += "EXEC_TIME: " + (System.currentTimeMillis() - start) + " ms\n";
        metrics += "EXEC_MOVE: " + execMove + '\n';
        metrics += "DATA_MOVE: " + dataMove + '\n';
        metrics += "DATA_WRITE: " + dataWrite + '\n';
        metrics += "DATA_READ: " + dataRead;
        Main.standardOutput(metrics);
    }

    /**
     * calculate the metrics with the current instance
     *
     * @param executionContext the current execution context of the program
     */
    @Override
    public void acknowledge(ExecutionContext executionContext)
    {
        execMove += 1;
        switch (executionContext.getCurrentInstruction().getMetricsType())
        {
            case DATA_WRITE:
                dataWrite += 1;
                break;
            case DATA_READ:
                dataRead += 1;
                break;
            case DATA_MOVE:
                dataMove += 1;
                break;
        }
    }
}
