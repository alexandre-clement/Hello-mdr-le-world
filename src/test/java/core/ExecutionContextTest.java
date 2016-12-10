package core;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author TANG Yi
 *         Created the 07/12/2016.
 */
public class ExecutionContextTest {

    private ExecutionContext context0;
    private ExecutionContext context1;

    @Before
    public void setUp() throws Exception {
        byte[] memory0 = new byte[ExecutionContext.CAPACITY];
        byte[] memory1= new byte[ExecutionContext.CAPACITY];
        memory1[1] = 30;

        context0 = new ExecutionContext(0, 0, memory0, null, null, null, null);
        context1 = new ExecutionContext(0, 1, memory1, null, null, null, null);
    }

    @Test
    public void printValue() throws Exception {
        assertEquals(0, context0.printValue());
        assertEquals(30, context1.printValue());
    }

    @Test
    public void getMemorySnapshot() throws Exception {
        assertEquals("C1:  30   ", context1.getMemorySnapshot());
        assertEquals(String.format("C%d:%4d   ", 1, 30), context1.getMemorySnapshot());
    }
}