package probe;

import core.Core;
import core.ExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class MetricsTest
{
    private Metrics metric;
    private ExecutionContext context;

    @Before
    public void setUp() throws Exception
    {
        metric = new Metrics(8);
        context = new ExecutionContext(Core.getExecutables(), null, null, null);
    }

    @Test
    public void metrics() throws Exception
    {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        metric.setOut(new PrintStream(byteArray));
        String[] lines;
        metric.initialize();

        // INCR
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 1", lines[3]);
        assertEquals("DATA_MOVE: 0", lines[4]);
        assertEquals("DATA_WRITE: 1", lines[5]);
        assertEquals("DATA_READ: 0", lines[6]);

        // DECR
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 2", lines[3]);
        assertEquals("DATA_MOVE: 0", lines[4]);
        assertEquals("DATA_WRITE: 2", lines[5]);
        assertEquals("DATA_READ: 0", lines[6]);

        // RIGHT
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 3", lines[3]);
        assertEquals("DATA_MOVE: 1", lines[4]);
        assertEquals("DATA_WRITE: 2", lines[5]);
        assertEquals("DATA_READ: 0", lines[6]);

        // LEFT
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 4", lines[3]);
        assertEquals("DATA_MOVE: 2", lines[4]);
        assertEquals("DATA_WRITE: 2", lines[5]);
        assertEquals("DATA_READ: 0", lines[6]);

        // OUT
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 5", lines[3]);
        assertEquals("DATA_MOVE: 2", lines[4]);
        assertEquals("DATA_WRITE: 2", lines[5]);
        assertEquals("DATA_READ: 1", lines[6]);

        // IN
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 6", lines[3]);
        assertEquals("DATA_MOVE: 2", lines[4]);
        assertEquals("DATA_WRITE: 3", lines[5]);
        assertEquals("DATA_READ: 1", lines[6]);

        // JUMP
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 7", lines[3]);
        assertEquals("DATA_MOVE: 2", lines[4]);
        assertEquals("DATA_WRITE: 3", lines[5]);
        assertEquals("DATA_READ: 2", lines[6]);

        // BACK
        byteArray.reset();
        context.nextInstruction();
        metric.acknowledge(context);
        metric.getResult();
        lines = byteArray.toString().split("\n");
        assertEquals("PROG_SIZE: 8", lines[1]);
        assertEquals("EXEC_MOVE: 8", lines[3]);
        assertEquals("DATA_MOVE: 2", lines[4]);
        assertEquals("DATA_WRITE: 3", lines[5]);
        assertEquals("DATA_READ: 3", lines[6]);
    }
}