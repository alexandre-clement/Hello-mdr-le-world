package interpreter;

import core.Core;
import core.Instructions;
import exception.ExitException;
import exception.OutOfMemoryException;
import exception.OverflowException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Alexandre Clement
 *         Created the 23/11/2016.
 */
public class CoreTest {

    @Test(expected = OutOfMemoryException.class)
    public void OutOfMemoryLeftTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if (("<".equals(instruction.getShortSyntax())))
                instruction.execute();
        }
    }

    @Test(expected = OverflowException.class)
    public void OverflowDecrementTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if (("-".equals(instruction.getShortSyntax())))
                instruction.execute();
        }
    }

    @Test
    public void IncrementTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if (("+".equals(instruction.getShortSyntax())))
                for (int i=0; i<255; i++)
                    instruction.execute();
        }
        assertEquals(255, core.getValue());
    }

    @Test(expected = OverflowException.class)
    public void OverflowIncrementTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if (("+".equals(instruction.getShortSyntax())))
                for (int i=0; i<256; i++)
                    instruction.execute();
        }
    }


    @Test
    public void RightTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if ((">".equals(instruction.getShortSyntax())))
                for (int i=0; i<Core.CAPACITY-1; i++)
                    instruction.execute();
        }
        assertEquals(Core.CAPACITY-1, core.getPointer());
    }

    @Test(expected = OutOfMemoryException.class)
    public void OutOfMemoryRightTest() throws ExitException {
        Core core = new Core(null);
        for (Instructions instruction : core.getInstructions()) {
            if ((">".equals(instruction.getShortSyntax())))
                for (int i=0; i<Core.CAPACITY; i++)
                    instruction.execute();
        }
    }
}
