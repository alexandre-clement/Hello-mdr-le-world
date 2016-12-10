package instructions;

import static org.junit.Assert.*;
import core.ExecutionContext;
import core.Instructions;
import exception.OutOfMemoryException;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author TANG Yi
 * Created on 07/12/2016.
 */
public class RightTest {

    @Test
    public void executeTest() throws Exception {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 3, memory, null, null, null, null);
        new Right().execute(context);
        assertEquals(4,context.getPointer());
    }

    @Test(expected = OutOfMemoryException.class)
    public void executeFailTest() throws Exception {
        byte[] memory = new byte[5];
        ExecutionContext context = new ExecutionContext(0, 4, memory, null, null, null, null);
        new Right().execute(context);
    }

}