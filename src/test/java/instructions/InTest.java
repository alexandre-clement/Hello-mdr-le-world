package instructions;

import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class InTest {
    @Before
    public void setUp() throws Exception {
        System.setIn(new ByteArrayInputStream(new byte[]{65, 100}));
    }

    @Test
    public void execute() throws Exception {
        ExecutionContext context = new ExecutionContext(0, 0, new byte[1], new Executable[]{new In()}, null, new InputStreamReader(System.in), null);
        context.execute();
        assertEquals(65, context.printValue());
        context.execute();
        assertEquals(100, context.printValue());

    }

}