package instructions;

import core.ExecutionContext;
import main.MainTest;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 07/12/2016.
 */
public class InTest
{
    @Before
    public void setUp() throws Exception
    {
        System.setIn(new ByteArrayInputStream(new byte[]{65, 100}));
        File file = new File(MainTest.INPUT);
        Writer write = new FileWriter(file);
        write.write("Hello world !\n");
        write.close();
    }

    @Test
    public void execute() throws Exception
    {
        ExecutionContext context = new ExecutionContext(0, 0, new byte[1], new Executable[]{new In()}, null, System.in, null);
        context.execute();
        assertEquals(65, context.printValue());
        context.execute();
        assertEquals(100, context.printValue());

    }

    @Test
    public void execute2() throws Exception
    {
        ExecutionContext context = new ExecutionContext(0, 0, new byte[1], new Executable[]{new In()}, null, new FileInputStream(MainTest.INPUT), null);
        context.execute();
        assertEquals(72, context.printValue());
        context.execute();
        assertEquals(101, context.printValue());
        context.execute();
        assertEquals(108, context.printValue());
        context.execute();
        assertEquals(108, context.printValue());
        context.execute();
        assertEquals(111, context.printValue());

    }

}