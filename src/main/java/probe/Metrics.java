package probe;

import core.Core;
import core.ExecutionContext;

/**
 * @author Alexandre Clement
 *         Created the 08/12/2016.
 * Calculate the metrics of the program and print them out using the method getResult
 */
public class Metrics implements Meter
{
    private long start;
    private long exec_move = 0;
    private long data_move = 0;
    private long data_write = 0;
    private long data_read = 0;
    private long length;

    public Metrics(long length)
    {
        this.length = length;
        exec_move = 0;
        data_move = 0;
        data_write = 0;
        data_read = 0;
        start = System.currentTimeMillis();
    }

    /**
     * Print out the metrics
     */
    @Override
    public void getResult()
    {
        String metrics = "\nPROG_SIZE: " + length + '\n';
        metrics += "EXEC_TIME: " + (System.currentTimeMillis() - start) + " ms\n";
        metrics += "EXEC_MOVE: " + exec_move + '\n';
        metrics += "DATA_MOVE: " + data_move + '\n';
        metrics += "DATA_WRITE: " + data_write + '\n';
        metrics += "DATA_READ: " + data_read;
        Core.standardOutput(metrics);
    }

    /**
     * calculate the metrics with the current instance
     * @param executionContext the current execution context of the program
     */
    @Override
    public void acknowledge(ExecutionContext executionContext)
    {
        exec_move += 1;
        switch (executionContext.getCurrentInstruction().getType())
        {
            case DATA_WRITE:
                data_write += 1;
                break;
            case DATA_READ:
                data_read += 1;
                break;
            case DATA_MOVE:
                data_move += 1;
                break;
        }
    }
}
