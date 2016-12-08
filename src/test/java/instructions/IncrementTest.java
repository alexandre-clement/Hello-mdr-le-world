
package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.OverflowException;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created on 07/12/2016.
 * @author TANG Yi
 */
public class IncrementTest {
    private ExecutionContext context;

    @Before
    public void init() {
        byte[] memory = new byte[1];
        memory[0] = ExecutionContext.MAX-1;
        context = new ExecutionContext(0, 0, memory, new Instructions[0], new HashMap<>(), new InputStreamReader(System.in), System.out);
    }

    @Test
    public void executeTest() throws Exception {
        new Increment().execute(context);
        assertEquals(255, context.printValue());
    }

    @Test(expected = OverflowException.class)
    public void executeFailTest() throws Exception {
        Increment increment = new Increment();
        increment.execute(context);
        increment.execute(context);
    }

}