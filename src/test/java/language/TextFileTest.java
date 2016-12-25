package language;

import exception.ExitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class TextFileTest
{
    private TextFile textFile;

    @Before
    public void setUp() throws Exception
    {
        textFile = new TextFile("src/test/test.bf");
    }

    @After
    public void after() throws ExitException
    {
        textFile.close();
    }

    @Test
    public void next() throws Exception
    {
        assertEquals("INCR", textFile.next());
        assertEquals("INCR", textFile.next());
        assertEquals("INCR", textFile.next());
        assertEquals("# CO: 3", textFile.next());
        assertEquals("RIGHT", textFile.next());
        assertEquals("++++ # C1: 4", textFile.next());
        assertEquals("<[->[->+>+<<]>[-<+>]<<]>[-] # C3: C0 * C1 = 12", textFile.next());
    }

    @Test
    public void close() throws Exception
    {
        textFile.close();
        try
        {
            textFile.next();
            fail("Stream isn't closed");
        }
        catch (ExitException exception)
        {
            assertEquals(127, exception.getExit());
            assertEquals("Stream Closed", exception.getMessage());
        }
    }

}