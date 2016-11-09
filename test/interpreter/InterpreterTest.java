package interpreter;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {

    @Test
    public void buildNothingTest() {
        Interpreter interpreter = new Interpreter().build("");
        assertNotNull(interpreter);
    }

    @Test
    public void buildTest() {
        Interpreter interpreter = new Interpreter().build("-i");
        assertEquals("-p", interpreter.getOptionSnapshot());
    }

    @Test
    public void noUniqueOptionTest() {
        assertTrue(new Interpreter().build("-p", "src/test.bf").noUniqueOption());
        assertFalse(new Interpreter().build("-p", "src/test.bf", "--rewrite", "--check").noUniqueOption());
    }

    @Test
    public void runTest() {

    }

    @Test
    public void resetFilenamesTest() {
        Filenames.source.setName("source");
        new Interpreter().resetFilenames();
        assertNull(Filenames.source.getName());
    }
}
