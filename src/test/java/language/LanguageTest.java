package language;

import interpreter.Interpreter;
import org.junit.Before;
import org.junit.Test;

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
        Interpreter noOption = Interpreter.buildInterpreter("-p", "src/test/test.bf");
        Interpreter image = Interpreter.buildInterpreter("-p", "src/test/test.bmp");
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
        assertEquals("src/test/test", imageLanguage.getFilename());
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