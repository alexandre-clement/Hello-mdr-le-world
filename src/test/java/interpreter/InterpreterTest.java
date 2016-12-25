package interpreter;

import exception.ExitException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Alexandre Clement
 * @since 07/12/2016.
 */
public class InterpreterTest
{
    private Interpreter noOption;
    private Interpreter oneOption;
    private Interpreter oneMetric;
    private Interpreter twoMetrics;
    private Interpreter metricsAndOption;
    private Interpreter fileOption;
    private Interpreter twoFilesOptions;

    @Before
    public void setUp() throws Exception
    {
        noOption = Interpreter.buildInterpreter("-p", "test.bf");
        oneOption = Interpreter.buildInterpreter("-p", "test.bf", "--rewrite");
        oneMetric = Interpreter.buildInterpreter("-p", "test.bf", "-m");
        twoMetrics = Interpreter.buildInterpreter("-p", "test.bf", "-m", "--trace");
        metricsAndOption = Interpreter.buildInterpreter("-p", "test.bf", "-m", "--rewrite");
        fileOption = Interpreter.buildInterpreter("-p", "test.bf", "-i", "input.txt");
        twoFilesOptions = Interpreter.buildInterpreter("-p", "test.bf", "-i", "input.txt", "-o", "output.txt");

    }

    @Test
    public void buildInterpreter() throws Exception
    {
        assertNotNull(noOption);
        assertNotNull(oneOption);
        assertNotNull(oneMetric);
        assertNotNull(twoMetrics);
        assertNotNull(metricsAndOption);
        assertNotNull(fileOption);
        assertNotNull(twoFilesOptions);

    }

    @Test
    public void getIllegalCommandLine()
    {
        try
        {
            Interpreter.buildInterpreter();
            fail("empty command line isn't accepted");
        }
        catch (ExitException exception)
        {
            assertEquals(126, exception.getExit());
        }
        try
        {
            Interpreter.buildInterpreter("");
            fail("command line without p option isn't accepted");
        }
        catch (ExitException exception)
        {
            assertEquals(126, exception.getExit());
        }
        try
        {
            Interpreter.buildInterpreter("-p");
            fail("commandline without source file isn't accepted");
        }
        catch (ExitException exception)
        {
            assertEquals(126, exception.getExit());
        }
        try
        {
            Interpreter.buildInterpreter("-p", "file.bf", "--rewrite", "--translate");
            fail("multiple standard output option isn't accepted");
        }
        catch (ExitException exception)
        {
            assertEquals(126, exception.getExit());
        }
        try
        {
            Interpreter.buildInterpreter("-p", "file.bf", "-i");
            fail("I/O options without file isn't accepted");
        }
        catch (ExitException exception)
        {
            assertEquals(126, exception.getExit());
        }
    }

    @Test
    public void getOptionValue() throws Exception
    {
        assertEquals("test.bf", noOption.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", oneOption.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", oneMetric.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", twoMetrics.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", metricsAndOption.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", fileOption.getOptionValue(Flag.PRINT));
        assertEquals("test.bf", twoFilesOptions.getOptionValue(Flag.PRINT));

        assertNull(noOption.getOptionValue(Flag.INPUT));
        assertNull(oneOption.getOptionValue(Flag.INPUT));
        assertNull(oneMetric.getOptionValue(Flag.INPUT));
        assertNull(twoMetrics.getOptionValue(Flag.INPUT));
        assertNull(metricsAndOption.getOptionValue(Flag.INPUT));
        assertEquals("input.txt", fileOption.getOptionValue(Flag.INPUT));
        assertEquals("input.txt", twoFilesOptions.getOptionValue(Flag.INPUT));

        assertNull(noOption.getOptionValue(Flag.OUTPUT));
        assertNull(oneOption.getOptionValue(Flag.OUTPUT));
        assertNull(oneMetric.getOptionValue(Flag.OUTPUT));
        assertNull(twoMetrics.getOptionValue(Flag.OUTPUT));
        assertNull(metricsAndOption.getOptionValue(Flag.OUTPUT));
        assertNull(fileOption.getOptionValue(Flag.OUTPUT));
        assertEquals("output.txt", twoFilesOptions.getOptionValue(Flag.OUTPUT));
    }

    @Test
    public void getProbes() throws Exception
    {
        assertEquals(0, noOption.getProbes().length);
        assertEquals(0, oneOption.getProbes().length);
        assertEquals(1, oneMetric.getProbes().length);
        assertEquals(2, twoMetrics.getProbes().length);
        assertEquals(1, metricsAndOption.getProbes().length);
        assertEquals(0, fileOption.getProbes().length);
        assertEquals(0, twoFilesOptions.getProbes().length);
    }

    @Test
    public void hasOption() throws Exception
    {
        assertTrue(noOption.hasOption(Flag.PRINT));
        assertTrue(oneOption.hasOption(Flag.PRINT));
        assertTrue(oneMetric.hasOption(Flag.PRINT));
        assertTrue(twoMetrics.hasOption(Flag.PRINT));
        assertTrue(metricsAndOption.hasOption(Flag.PRINT));
        assertTrue(fileOption.hasOption(Flag.PRINT));
        assertTrue(twoFilesOptions.hasOption(Flag.PRINT));

        assertFalse(noOption.hasOption(Flag.REWRITE));
        assertTrue(oneOption.hasOption(Flag.REWRITE));
        assertFalse(oneMetric.hasOption(Flag.REWRITE));
        assertFalse(twoMetrics.hasOption(Flag.REWRITE));
        assertTrue(metricsAndOption.hasOption(Flag.REWRITE));
        assertFalse(fileOption.hasOption(Flag.REWRITE));
        assertFalse(twoFilesOptions.hasOption(Flag.REWRITE));

        assertFalse(noOption.hasOption(Flag.METRICS));
        assertFalse(oneOption.hasOption(Flag.METRICS));
        assertTrue(oneMetric.hasOption(Flag.METRICS));
        assertTrue(twoMetrics.hasOption(Flag.METRICS));
        assertTrue(metricsAndOption.hasOption(Flag.METRICS));
        assertFalse(fileOption.hasOption(Flag.METRICS));
        assertFalse(twoFilesOptions.hasOption(Flag.METRICS));

        assertFalse(noOption.hasOption(Flag.INPUT));
        assertFalse(oneOption.hasOption(Flag.INPUT));
        assertFalse(oneMetric.hasOption(Flag.INPUT));
        assertFalse(twoMetrics.hasOption(Flag.INPUT));
        assertFalse(metricsAndOption.hasOption(Flag.INPUT));
        assertTrue(fileOption.hasOption(Flag.INPUT));
        assertTrue(twoFilesOptions.hasOption(Flag.INPUT));
    }

    @Test
    public void getOptions() throws Exception
    {
        assertEquals(1, noOption.getOptions().length);
        assertTrue(noOption.getOptions()[0] == Flag.PRINT);

        assertEquals(1, oneOption.getOptions().length);
        assertTrue(oneOption.getOptions()[0] == Flag.REWRITE);

        assertEquals(2, oneMetric.getOptions().length);
        assertTrue(oneMetric.getOptions()[0] == Flag.PRINT);
        assertTrue(oneMetric.getOptions()[1] == Flag.METRICS);

        assertEquals(3, twoMetrics.getOptions().length);
        assertTrue(twoMetrics.getOptions()[0] == Flag.PRINT);
        assertTrue(twoMetrics.getOptions()[1] == Flag.METRICS);
        assertTrue(twoMetrics.getOptions()[2] == Flag.TRACE);

        assertEquals(2, metricsAndOption.getOptions().length);
        assertTrue(metricsAndOption.getOptions()[1] == Flag.METRICS);
        assertTrue(metricsAndOption.getOptions()[0] == Flag.REWRITE);

        assertEquals(2, fileOption.getOptions().length);
        assertTrue(fileOption.getOptions()[0] == Flag.PRINT);
        assertTrue(fileOption.getOptions()[1] == Flag.INPUT);

        assertEquals(3, twoFilesOptions.getOptions().length);
        assertTrue(twoFilesOptions.getOptions()[0] == Flag.PRINT);
        assertTrue(twoFilesOptions.getOptions()[1] == Flag.INPUT);
        assertTrue(twoFilesOptions.getOptions()[2] == Flag.OUTPUT);
    }

}