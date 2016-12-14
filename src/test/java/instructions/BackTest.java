package instructions;

import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class BackTest {
    private ExecutionContext simpleLoopContext;
    private ExecutionContext loopInLoopContext;

    @Before
    public void setUp() throws Exception {
        byte[] memory1 = new byte[] {ExecutionContext.MAX};
        byte[] memory2 = new byte[] {ExecutionContext.MAX, ExecutionContext.MAX, ExecutionContext.MAX, 0};
        Executable[] simpleLoop = new Executable[] {new Jump(), new Decrement(), new Back()};
        Executable[] loopInLoop = new Executable[] {new Jump(), new Jump(), new Decrement(), new Back(), new Right(), new Back()};
        simpleLoopContext = new ExecutionContext(0, 0, memory1, simpleLoop, null, null, null);
        loopInLoopContext = new ExecutionContext(0, 0, memory2, loopInLoop, null, null, null);
    }

    @Test
    public void execute() throws Exception {
        for (int instruction = 0; simpleLoopContext.hasNextInstruction(); simpleLoopContext.nextInstruction())
            simpleLoopContext.execute();

        for (int instruction = 0; loopInLoopContext.hasNextInstruction(); loopInLoopContext.nextInstruction())
            loopInLoopContext.execute();

        assertEquals(0, simpleLoopContext.getValue());
        assertEquals(3, loopInLoopContext.getPointer());
        for (int i = 0; i < 3; i++)
        {
            assertEquals(0, loopInLoopContext.printValue(i));
        }
    }

}