package interpreter;

import static org.junit.Assert.*;

import exception.IllegalCommandlineException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {

    private Interpreter interpreter;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        interpreter = new Interpreter();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void missingPrintOption() {
        try {
            interpreter.build("");
        } catch (IllegalCommandlineException exception) {
            assertEquals("Missing required option: p", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    @Test
    public void missingPrintArgument() {
        try {
            interpreter.build("-p");
        } catch (IllegalCommandlineException exception) {
            assertEquals("Missing argument for option: p", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    @Test
    public void getOptTest() throws IllegalCommandlineException {
        assertTrue(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.PRINT));
        assertTrue(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.REWRITE));

        assertFalse(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.CHECK));
        assertFalse(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.INPUT));
    }

    @Test
    public void getArgTest() throws IllegalCommandlineException {
        assertEquals(   "test.bf",      interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.PRINT));
        assertEquals(   "input.txt",    interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.INPUT));

        assertNotSame(  "test.b",       interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.PRINT));
        assertNotSame(  "i.txt",        interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.INPUT));

        assertNull(                     interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.OUTPUT));
    }

    @Test
    public void hasStandardOutputOption() throws IllegalCommandlineException {
        assertFalse(interpreter.build("-p", "test.bf").hasStandardOutputOption());
        assertTrue(interpreter.build("-p", "test.bf", "--rewrite").hasStandardOutputOption());
    }

    @Test
    public void hasMultipleStandardOutputOption() {
        try {
            interpreter.build("-p", "test.bf", "--rewrite", "--check").hasStandardOutputOption();
        } catch (IllegalCommandlineException exception) {
            assertEquals("The option 'check' was specified but an option from this group has already been selected: 'rewrite'", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    @Test
    public void hasMultipleOption() throws IllegalCommandlineException {
        interpreter.build("-p", "test.bf", "-i", "input.txt", "-o", "output.txt", "--rewrite");
    }

    @Test
    public void helpTest() throws IllegalCommandlineException {
        interpreter.build("-h");
    }

    @Test
    public void versionTest() throws IllegalCommandlineException {
        interpreter.build("-v");
    }
}
