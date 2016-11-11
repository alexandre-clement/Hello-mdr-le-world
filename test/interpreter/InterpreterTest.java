package interpreter;

import static org.junit.Assert.*;

import exception.IllegalCommandlineOptionsException;
import org.junit.Test;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {

    @Test
    public void getOptTest() throws IllegalCommandlineOptionsException {
        assertTrue(new Interpreter().build("--rewrite", "-p", "test.bf").hasOption(Flag.p));
        assertTrue(new Interpreter().build("--rewrite", "-p", "test.bf").hasOption(Flag.rewrite));

        assertFalse(new Interpreter().build("--rewrite", "-p", "test.bf").hasOption(Flag.check));
        assertFalse(new Interpreter().build("--rewrite", "-p", "test.bf").hasOption(Flag.i));
    }

    @Test
    public void getArgTest() throws IllegalCommandlineOptionsException {
        assertEquals("test.bf", new Interpreter().build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.p));
        assertEquals("input.txt", new Interpreter().build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.i));

        assertNotSame("test.b", new Interpreter().build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.p));
        assertNotSame("i.txt", new Interpreter().build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.i));

        assertNull(new Interpreter().build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.o));
    }

    @Test
    public void hasStandardOutputOption() throws IllegalCommandlineOptionsException {
        assertFalse(new Interpreter().build("-p", "test.bf").hasStandardOutputOption());
        assertTrue(new Interpreter().build("-p", "test.bf", "--rewrite").hasStandardOutputOption());
    }

    @Test
    public void hasMultipleStandardOutputOption() {
        try {
            new Interpreter().build("-p", "test.bf", "--rewrite", "--check").hasStandardOutputOption();
        } catch (IllegalCommandlineOptionsException exception) {
            assertEquals("Multiple standard output options", exception.getMessage());
            assertEquals(127, exception.getExit());
        }
    }


    @Test
    public void missingPrintOption() {
        try {
            new Interpreter().build("");
        } catch (IllegalCommandlineOptionsException exception) {
            assertEquals("Missing required option: p", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    @Test
    public void missingPrintArgument() {
        try {
            new Interpreter().build("-p");
        } catch (IllegalCommandlineOptionsException exception) {
            assertEquals("Missing argument for option: p", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }
}
