package instructions;

import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class BackOptimisedTest
{
    private ExecutionContext simpleLoopOptimisedContext;
    private ExecutionContext loopInLoopOptimisedContext;

    @Before
    public void setUp() throws Exception
    {
        byte[] memory1 = new byte[]{ExecutionContext.MAX};
        byte[] memory2 = new byte[]{ExecutionContext.MAX, ExecutionContext.MAX, ExecutionContext.MAX, 0};
        Executable[] simpleLoop = new Executable[]{new JumpOptimised(), new Decrement(), new BackOptimised()};
        HashMap<Integer, Integer> jumptable1 = new HashMap<>();
        jumptable1.put(2, 0);
        Executable[] loopInLoop = new Executable[]{new JumpOptimised(), new JumpOptimised(), new Decrement(), new BackOptimised(), new Right(), new BackOptimised()};
        HashMap<Integer, Integer> jumptable2 = new HashMap<>();
        jumptable2.put(3, 1);
        jumptable2.put(5, 0);
        simpleLoopOptimisedContext = new ExecutionContext(0, 0, memory1, simpleLoop, jumptable1, null, null);
        loopInLoopOptimisedContext = new ExecutionContext(0, 0, memory2, loopInLoop, jumptable2, null, null);
    }

    @Test
    public void open() throws Exception
    {
        assertFalse(new BackOptimised().open());
    }

    @Test
    public void execute() throws Exception
    {
        for (int instruction = 0; simpleLoopOptimisedContext.hasNextInstruction(); simpleLoopOptimisedContext.nextInstruction())
            simpleLoopOptimisedContext.execute();

        for (int instruction = 0; loopInLoopOptimisedContext.hasNextInstruction(); loopInLoopOptimisedContext.nextInstruction())
            loopInLoopOptimisedContext.execute();

        assertEquals(0, simpleLoopOptimisedContext.getValue());
        assertEquals(3, loopInLoopOptimisedContext.getPointer());
        for (int i = 0; i < 3; i++)
        {
            assertEquals(0, loopInLoopOptimisedContext.printValue(i));
        }
    }
}