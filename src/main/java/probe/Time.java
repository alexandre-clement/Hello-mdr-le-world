package probe;

import core.ExecutionContext;
import main.Main;

/**
 * Time the program execution.
 *
 * @author Alexandre Clement
 * @see Meter
 * @since 16/12/2016.
 */
public class Time implements Meter
{
    private long start;

    /**
     * Initialize timer.
     */
    @Override
    public void initialize()
    {
        start = System.currentTimeMillis();
    }

    /**
     * Print the execution time.
     */
    @Override
    public void getResult()
    {
        Main.standardOutput("\nEXEC_TIME: " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * Do nothing.
     *
     * @param executionContext the current execution context of the program
     */
    @Override
    public void acknowledge(ExecutionContext executionContext)
    {
        // Nothing to do while executing the program
    }
}
