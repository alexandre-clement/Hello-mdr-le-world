package probe;

import core.ExecutionContext;

/**
 * @author Alexandre Clement
 *         Created the 08/12/2016.
 */
public interface Meter
{
    void initialize();

    void getResult();

    /**
     * @param executionContext the current execution context of the program
     */
    void acknowledge(ExecutionContext executionContext);
}
