package instructions;

import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class JumpTest
{
    private ExecutionContext simpleLoopContext;
    private ExecutionContext loopInLoopContext;

    @Before
    public void setUp() throws Exception
    {
        byte[] memory1 = new byte[]{0};
        byte[] memory2 = new byte[]{0};
        Executable[] simpleLoop = new Executable[]{new Jump(), new Decrement(), new Back()};
        Executable[] loopInLoop = new Executable[]{new Jump(), new Jump(), new Decrement(), new Back(), new Right(), new Back()};
        simpleLoopContext = new ExecutionContext(0, 0, memory1, simpleLoop, null, null, null);
        loopInLoopContext = new ExecutionContext(0, 0, memory2, loopInLoop, null, null, null);
    }

    @Test
    public void open() throws Exception
    {
        assertTrue(new Jump().open());
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
        while (simpleLoopContext.hasNextInstruction())
        {
            simpleLoopContext.execute();
            simpleLoopContext.nextInstruction();
        }
        while (loopInLoopContext.hasNextInstruction())
        {
            loopInLoopContext.execute();
            loopInLoopContext.nextInstruction();
        }
    }

}