package instructions;

import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class JumpOptimisedTest
{
    private ExecutionContext simpleLoopOptimisedContext;
    private ExecutionContext loopInLoopOptimisedContext;

    @Before
    public void setUp() throws Exception
    {
        byte[] memory1 = new byte[]{0};
        byte[] memory2 = new byte[]{0};
        Executable[] simpleLoop = new Executable[]{new JumpOptimised(), new Decrement(), new BackOptimised()};
        HashMap<Integer, Integer> jumptable1 = new HashMap<>();
        jumptable1.put(0, 2);
        Executable[] loopInLoop = new Executable[]{new JumpOptimised(), new JumpOptimised(), new Decrement(), new BackOptimised(), new Right(), new BackOptimised()};
        HashMap<Integer, Integer> jumptable2 = new HashMap<>();
        jumptable2.put(0, 5);
        simpleLoopOptimisedContext = new ExecutionContext(0, 0, memory1, simpleLoop, jumptable1, null, null);
        loopInLoopOptimisedContext = new ExecutionContext(0, 0, memory2, loopInLoop, jumptable2, null, null);
    }

    @Test
    public void open() throws Exception
    {
        assertTrue(new JumpOptimised().open());
    }

    /**
     * Si le test ne creer pas une erreur,
     * cela signifie que l'on passe bien de l'instruction JUMP à l'instruction BACK
     * sans execute les instructions intermédiaire
     * (dans le cas contraire, un overflow serait lancé du à l'instruction DECR sur une cellule à 0)
     */
    @Test
    public void execute() throws Exception
    {
        while (simpleLoopOptimisedContext.hasNextInstruction())
        {
            simpleLoopOptimisedContext.execute();
            simpleLoopOptimisedContext.nextInstruction();
        }
        while (loopInLoopOptimisedContext.hasNextInstruction())
        {
            loopInLoopOptimisedContext.execute();
            loopInLoopOptimisedContext.nextInstruction();
        }
    }

}