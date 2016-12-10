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
public class DecrementTest {
    private ExecutionContext context;

    @Before
    public void init() {
        byte[] memory = new byte[1];
        memory[0] = 1;
        context = new ExecutionContext(0, 0, memory, null, null, null, null);
    }

    @Test
    public void executeTest() throws Exception {
        new Decrement().execute(context);
        assertEquals(0, context.printValue());
    }

    @Test(expected = OverflowException.class)
    public void executeFailTest() throws Exception {
        Decrement decrement = new Decrement();
        decrement.execute(context);
        decrement.execute(context);
    }

}