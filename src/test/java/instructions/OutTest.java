package instructions;

import core.ExecutionContext;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class OutTest
{
    @Test
    public void execute() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExecutionContext context = new ExecutionContext(0, 0, new byte[]{65}, new Executable[]{new Out()}, null, null, new PrintStream(out));
        context.execute();
        assertEquals("A", out.toString());
    }

}