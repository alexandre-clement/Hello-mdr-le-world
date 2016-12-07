
package instructions;

import core.Core;
import core.ExecutionContext;
import core.Instructions;
import org.junit.Assert;
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
        byte[] memory = new byte[ExecutionContext.CAPACITY];
        context = new ExecutionContext(0, 3, memory, new Instructions[0], new HashMap<>(), new InputStreamReader(System.in), System.out);
    }

    @Test
    public void executeTest() throws Exception {
        Increment incr = new Increment();
        incr.execute(context);
        assertEquals(1, context.memory[3]);
    }

}