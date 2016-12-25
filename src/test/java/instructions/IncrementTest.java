
package instructions;

import core.ExecutionContext;
import exception.OverflowException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 07/12/2016.
 * @author TANG Yi
 *
 * Test for Increment Instruction
 */
public class IncrementTest {

    /**
     * Increment 255 fois une cellule memoire
     * @throws Exception OverflowException lorsque la cellule depasse la valeur max i.e 255
     */
    @Test(expected = OverflowException.class)
    public void emptyFailTest() throws Exception {
        ExecutionContext empty = new ExecutionContext(0, 0, new byte[1]);
        Increment increment = new Increment();
        for (int i = 1; i < 256; i++)
        {
            increment.execute(empty);
            assertEquals(i, empty.printValue());
        }
        increment.execute(empty);
    }

}