package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author TANG Yi
 * @since  07/12/2016.
 */
public class ExecutionContextTest {

    private ExecutionContext context0;
    private ExecutionContext context1;

    @Before
    public void setUp() throws Exception {
        byte[] memory0 = new byte[ExecutionContext.CAPACITY];
        byte[] memory1= new byte[ExecutionContext.CAPACITY];
        memory1[1] = 30;

        context0 = new ExecutionContext(0, 0, memory0);
        context1 = new ExecutionContext(0, 1, memory1);
    }

    @After
    public void tearDown() throws Exception
    {

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

    @Test
    public void execute() throws Exception
    {

    }

    @Test
    public void getInstruction() throws Exception
    {

    }

    @Test
    public void nextInstruction() throws Exception
    {

    }

    @Test
    public void previousInstruction() throws Exception
    {

    }

    @Test
    public void getPointer() throws Exception
    {

    }

    @Test
    public void hasNextCell() throws Exception
    {

    }

    @Test
    public void nextCell() throws Exception
    {

    }

    @Test
    public void hasPreviousCell() throws Exception
    {

    }

    @Test
    public void previousCell() throws Exception
    {

    }

    @Test
    public void increment() throws Exception
    {

    }

    @Test
    public void decrement() throws Exception
    {

    }

    @Test
    public void in() throws Exception
    {

    }

    @Test
    public void readNextValue() throws Exception
    {

    }

    @Test
    public void out() throws Exception
    {

    }

    @Test
    public void getValue() throws Exception
    {

    }

    @Test
    public void getCurrentExecutable() throws Exception
    {

    }

    @Test
    public void getCurrentInstruction() throws Exception
    {

    }

    @Test
    public void getProgramLength() throws Exception
    {

    }

    @Test
    public void hasNextInstruction() throws Exception
    {

    }

    @Test
    public void hasPreviousInstruction() throws Exception
    {

    }

    @Test
    public void bound() throws Exception
    {

    }

    @Test
    public void getJumpLink() throws Exception
    {

    }

    @Test
    public void close() throws Exception
    {

    }
}