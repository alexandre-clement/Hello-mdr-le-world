package probe;

import core.ExecutionContext;
import main.Main;

/**
 * @author Alexandre Clement
 *         Created the 16/12/2016.
 *
 * Time the program execution
 */
public class Time implements Meter
{
    private long start;

    @Override
    public void initialize()
    {
        start = System.currentTimeMillis();
    }

    @Override
    public void getResult()
    {
        Main.standardOutput("\nEXEC_TIME: " + (System.currentTimeMillis() - start) + " ms");
    }

    @Override
    public void acknowledge(ExecutionContext executionContext)
    {

    }
}
