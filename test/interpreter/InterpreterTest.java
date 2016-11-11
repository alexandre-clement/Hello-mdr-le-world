package interpreter;

import static org.junit.Assert.*;

import exception.IllegalCommandlineOptionsException;
import exception.MultipleStandardOutputOptionsException;
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
    public void hasStandardOutputOption() throws IllegalCommandlineOptionsException, MultipleStandardOutputOptionsException {
        assertFalse(new Interpreter().build("-p", "test.bf").hasStandardOutputOption());
        assertTrue(new Interpreter().build("-p", "test.bf", "--rewrite").hasStandardOutputOption());
    }

    @Test(expected = IllegalCommandlineOptionsException.class)
    public void hasMultipleStandardOutputOption() throws IllegalCommandlineOptionsException {
        new Interpreter().build("-p", "test.bf", "--rewrite", "--check").hasStandardOutputOption();
    }


    @Test(expected = IllegalCommandlineOptionsException.class)
    public void missingPrintOption() throws IllegalCommandlineOptionsException {
        new Interpreter().build("--rewrite").hasOption(Flag.rewrite);
    }

    @Test(expected = IllegalCommandlineOptionsException.class)
    public void missingPrintArgument() throws IllegalCommandlineOptionsException {
        new Interpreter().build("-p").hasOption(Flag.p);
    }
}
