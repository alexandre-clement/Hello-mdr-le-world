package probe;

import core.ExecutionContext;
import core.ExecutionContextBuilder;
import instructions.Increment;
import main.MainTest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class TraceTest
{
    private Trace trace;
    private ExecutionContext context;

    @Before
    public void setUp() throws Exception
    {
        trace = new Trace(MainTest.PATH);
        context = new ExecutionContextBuilder().build();
    }

    @Test
    public void traceTest() throws Exception
    {
        trace.initialize();
        trace.acknowledge(context);
        new Increment().execute(context);
        context.nextInstruction();
        trace.acknowledge(context);
        trace.getResult();
        Scanner scanner = new Scanner(new File(MainTest.PATH + ".log"));
        assertEquals("Execution step        Execution pointer        Data pointer             Memory snapshot", scanner.nextLine());
        assertEquals("", scanner.nextLine());
        assertEquals("1                     0                        0                        ", scanner.nextLine());
        assertEquals("2                     1                        0                        C0:   1   ", scanner.nextLine());
    }

}