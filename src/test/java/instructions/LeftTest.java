
package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.OutOfMemoryException;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author TANG Yi
 * Created on 07/12/2016.
 */
public class LeftTest {

    @Test
    public void executeTest() throws Exception {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 1, memory, new Instructions[0], new HashMap<>(), new InputStreamReader(System.in), System.out);
        new Left().execute(context);
        assertEquals(0,context.getPointer());
    }

    @Test(expected = OutOfMemoryException.class)
    public void executeFailTest() throws Exception {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 0, memory, new Instructions[0], new HashMap<>(), new InputStreamReader(System.in), System.out);
        new Left().execute(context);
    }

}