package instructions;

import core.ExecutionContext;
import core.Instructions;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class BackTest {
    private ExecutionContext context1;
    private ExecutionContext context2;
    @Before
    public void setUp() throws Exception {
        byte[] memory = new byte[] {ExecutionContext.MAX};
        context1 = new ExecutionContext(0, 0, memory, new Executable[] {new Jump(), new Decrement(), new Back()}, null, null, null);
        context2 = new ExecutionContext(0, 0, memory, new Executable[] {new Jump(), new Decrement(), new Back()}, null, null, null);
    }

    @Test
    public void execute() throws Exception {
        for (int instruction = 0; context1.hasNextInstruction(); context1.nextInstruction())
            context1.execute();
        assertEquals(0, context1.getValue());
    }

}