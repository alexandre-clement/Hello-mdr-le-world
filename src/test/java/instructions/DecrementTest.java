package instructions;

import core.ExecutionContext;
import exception.OverflowException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 07/12/2016.
 * @author TANG Yi
 */
public class DecrementTest {

    /**
     * Decrement 255 fois une cellule mémoire
     * @throws Exception OverflowException lorsque la cellule dépasse la valeur max i.e 255
     */
    @Test(expected = OverflowException.class)
    public void emptyFailTest() throws Exception {
        ExecutionContext full = new ExecutionContext(0, 0, new byte[]{ExecutionContext.MAX});
        Decrement decrement = new Decrement();
        for (int i = 254; i >= 0; i--)
        {
            decrement.execute(full);
            assertEquals(i, full.printValue());
        }
        decrement.execute(full);
    }

}