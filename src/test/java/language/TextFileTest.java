package language;

import exception.ExitException;
import main.MainTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

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
        File file = new File(MainTest.FILENAME);
        FileWriter write = new FileWriter(file);
        write.write("macro MULTI_INCR nb_INCR # definition de la macro MULTI_INCR\n" +
                "    apply nb_INCR on\n" +
                "        INCR\n" +
                "\n" +
                "MULTI_INCR 3\n" +
                "# CO: 3\n" +
                "RIGHT\n" +
                "++++ # C1: 4\n" +
                "<[->[->+>+<<]>[-<+>]<<]>[-] # C3: C0 * C1 = 12");
        write.close();
        textFile = new TextFile(MainTest.FILENAME);
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