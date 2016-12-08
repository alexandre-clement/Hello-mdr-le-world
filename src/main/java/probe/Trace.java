package probe;

import core.ExecutionContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Alexandre Clement
 *         Created the 08/12/2016.
 * Create the trace of the file in a p.log file
 */
public class Trace implements Meter
{
    private long step;
    private Writer writer;

    public Trace(String filename)
    {
        step = 0;

        try
        {
            writer = new FileWriter(filename + ".log");
        }
        catch (IOException e)
        {
            System.err.println("This should never happen");
        }
    }

    /**
     * Close the logfile
     */
    @Override
    public void getResult()
    {
        try
        {
            writer.close();
        }
        catch (IOException e)
        {
            System.err.println("This should never happen");
        }
    }

    /**
     * Add the context in log
     *
     * @param executionContext the current execution context of the program
     */
    @Override
    public void acknowledge(ExecutionContext executionContext)
    {
        String log = String.format("Execution step: %10d | Execution pointer: %10d | Data pointer: %10d | %s%n", ++step, executionContext.getInstruction(), executionContext.getPointer(), executionContext.getMemorySnapshot());
        try
        {
            writer.write(log);
            writer.flush();
        }
        catch (IOException e)
        {
            System.err.println("This should never happen");
        }
    }
}
