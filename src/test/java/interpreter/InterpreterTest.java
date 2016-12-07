package interpreter;

import exception.IllegalCommandlineException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class InterpreterTest {
    private Interpreter noOption;
    private Interpreter oneOption;
    private Interpreter oneMetric;
    private Interpreter twoMetrics;
    private Interpreter metricsAndOption;
    private Interpreter fileOption;
    private Interpreter twoFilesOptions;

    @Before
    public void setUp() throws Exception {
        noOption = Interpreter.buildInterpreter("-p", "test.bf");
        oneOption = Interpreter.buildInterpreter("-p", "test.bf", "--rewrite");
        oneMetric = Interpreter.buildInterpreter("-p", "test.bf", "-m");
        twoMetrics = Interpreter.buildInterpreter("-p", "test.bf", "-m", "--trace");
        metricsAndOption = Interpreter.buildInterpreter("-p", "test.bf", "-m", "--rewrite");
        fileOption =  Interpreter.buildInterpreter("-p", "test.bf", "-i", "input.txt");
        twoFilesOptions =  Interpreter.buildInterpreter("-p", "test.bf", "-i", "input.txt", "-o", "output.txt");

    }

    @Test
    public void buildInterpreter() throws Exception {
        try {
            Interpreter.buildInterpreter("");
            throw new AssertionError();
        } catch (IllegalCommandlineException e) {
            assertEquals(126, e.getExit());
        }
        try {
            Interpreter.buildInterpreter("-p");
            throw new AssertionError();
        } catch (IllegalCommandlineException e) {
            assertEquals(126, e.getExit());
        }
        try {
            Interpreter.buildInterpreter("-p", "test.bf", "--rewrite", "--translate");
            throw new AssertionError();
        } catch (IllegalCommandlineException e) {
            assertEquals(126, e.getExit());
        }
        try {
            Interpreter.buildInterpreter("-p", "test.bf", "-i");
            throw new AssertionError();
        } catch (IllegalCommandlineException e) {
            assertEquals(126, e.getExit());
        }

        assertNotNull(noOption);
        assertNotNull(oneOption);
        assertNotNull(oneMetric);
        assertNotNull(twoMetrics);
        assertNotNull(metricsAndOption);
        assertNotNull(fileOption);
        assertNotNull(twoFilesOptions);

    }

    @Test
    public void getOptionValue() throws Exception {
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
    public void hasOption() throws Exception {
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
    public void getOptions() throws Exception {
        assertEquals(1, noOption.getOptions().size());
        assertTrue(noOption.getOptions().contains(Flag.PRINT));

        assertEquals(1, oneOption.getOptions().size());
        assertTrue(oneOption.getOptions().contains(Flag.REWRITE));

        assertEquals(2, oneMetric.getOptions().size());
        assertTrue(oneMetric.getOptions().contains(Flag.PRINT));
        assertTrue(oneMetric.getOptions().contains(Flag.METRICS));

        assertEquals(3, twoMetrics.getOptions().size());
        assertTrue(twoMetrics.getOptions().contains(Flag.PRINT));
        assertTrue(twoMetrics.getOptions().contains(Flag.METRICS));
        assertTrue(twoMetrics.getOptions().contains(Flag.TRACE));

        assertEquals(2, metricsAndOption.getOptions().size());
        assertTrue(metricsAndOption.getOptions().contains(Flag.METRICS));
        assertTrue(metricsAndOption.getOptions().contains(Flag.REWRITE));

        assertEquals(2, fileOption.getOptions().size());
        assertTrue(fileOption.getOptions().contains(Flag.PRINT));
        assertTrue(fileOption.getOptions().contains(Flag.INPUT));

        assertEquals(3, twoFilesOptions.getOptions().size());
        assertTrue(twoFilesOptions.getOptions().contains(Flag.PRINT));
        assertTrue(twoFilesOptions.getOptions().contains(Flag.INPUT));
        assertTrue(twoFilesOptions.getOptions().contains(Flag.OUTPUT));
    }

}