package interpreter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {

    @Test
    public void buildTest() {
        String[] commandline = new String[] {"-p", "src/test.bf"};
        Interpreter interpreter = new Interpreter().build(commandline);
        // do some test
    }

    @Test
    public void runTest() {

    }

    @Test
    public void resetFilenamesTest() {
        Filenames.source.setName("source");
        new Interpreter().resetFilenames();
        Assert.assertNull(Filenames.source.getName());
    }
}
