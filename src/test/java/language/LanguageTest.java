package language;

import core.Core;
import core.ExecutionContext;
import core.ExecutionContextBuilder;
import interpreter.Interpreter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class LanguageTest
{
    private Language noOptionLanguage;
    private Language twoFilesOptionsLanguage;
    private Language imageLanguage;

    @Before
    public void setUp() throws Exception
    {
        File file = new File("src/test/test.bf");
        FileWriter write = new FileWriter(file);
        write.write("macro MULTI_INCR nb_INCR # definition de la macro MULTI_DECR\n" +
                "    apply nb_INCR on\n" +
                "        INCR\n" +
                "\n" +
                "MULTI_INCR 3\n" +
                "# CO: 3\n" +
                "RIGHT\n" +
                "++++ # C1: 4\n" +
                "<[->[->+>+<<]>[-<+>]<<]>[-] # C3: C0 * C1 = 12");
        write.close();
        file = new File("src/test/input.txt");
        write = new FileWriter(file);
        write.write("Hello world !\n");
        write.close();
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf", "--translate");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());
        new Core("src/test/test").run(interpreter.getOptions(), interpreter.getProbes(), context);

        Interpreter noOption = Interpreter.buildInterpreter("-p", "src/test/test.bf");
        Interpreter image = Interpreter.buildInterpreter("-p", "src/test/test_out.bmp");
        Interpreter twoFilesOptions = Interpreter.buildInterpreter("-p", "src/test/test.bf", "-i", "src/test/input.txt", "-o", "src/test/output.txt");
        noOptionLanguage = new Language(noOption);
        imageLanguage = new Language(image);
        twoFilesOptionsLanguage = new Language(twoFilesOptions);
    }

    @Test
    public void getFile() throws Exception
    {
        assertNotNull(noOptionLanguage.getFile());
        assertNotNull(imageLanguage.getFile());
        assertNotNull(twoFilesOptionsLanguage.getFile());
    }

    @Test
    public void getFilename() throws Exception
    {
        assertEquals("src/test/test", noOptionLanguage.getFilename());
        assertEquals("src/test/test_out", imageLanguage.getFilename());
        assertEquals("src/test/test", twoFilesOptionsLanguage.getFilename());
    }

    @Test
    public void getIn() throws Exception
    {
        assertNotNull(noOptionLanguage.getIn());
        assertNotNull(twoFilesOptionsLanguage.getIn());
    }

    @Test
    public void getOut() throws Exception
    {
        assertNotNull(noOptionLanguage.getOut());
        assertNotNull(twoFilesOptionsLanguage.getOut());
    }

}