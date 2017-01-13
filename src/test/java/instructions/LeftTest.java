package instructions;

import core.ExecutionContext;
import exception.OutOfMemoryException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author TANG Yi
 *         Created on 07/12/2016.
 */
public class LeftTest
{

    @Test
    public void executeTest() throws Exception
    {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 1, memory, null, null, null, null);
        new Left().execute(context);
        assertEquals(0, context.getPointer());
    }

    @Test(expected = OutOfMemoryException.class)
    public void executeFailTest() throws Exception
    {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 0, memory, null, null, null, null);
        new Left().execute(context);
    }

}