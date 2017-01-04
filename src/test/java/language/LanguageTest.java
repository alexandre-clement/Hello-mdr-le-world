package language;

import core.Core;
import core.ExecutionContext;
import core.ExecutionContextBuilder;
import interpreter.Interpreter;
import main.MainTest;
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
        file = new File(MainTest.INPUT);
        write = new FileWriter(file);
        write.write("Hello world !\n");
        write.close();
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME, "--translate");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());
        new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context);

        Interpreter noOption = Interpreter.buildInterpreter("-p", MainTest.FILENAME);
        Interpreter image = Interpreter.buildInterpreter("-p", MainTest.BITMAP);
        Interpreter twoFilesOptions = Interpreter.buildInterpreter("-p", MainTest.FILENAME, "-i", MainTest.INPUT, "-o", MainTest.OUTPUT);
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
        assertEquals("target/test", noOptionLanguage.getFilename());
        assertEquals("target/test_out", imageLanguage.getFilename());
        assertEquals("target/test", twoFilesOptionsLanguage.getFilename());
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