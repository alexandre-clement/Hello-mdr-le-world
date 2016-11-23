package interpreter;

import Language.Language;
import core.Core;
import exception.ExitException;
import exception.IllegalCommandlineException;
import exception.LanguageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Alexandre Clement
 *         Created the 23/11/2016.
 */
public class LanguageTest {

    private Writer out;
    private Interpreter interpreter;
    private Language language;

    @Before
    public void setLanguage() throws IOException {
        // Write to temp file
        out = new FileWriter(new File("temp.bf"));
    }

    @After
    public void close() throws LanguageException, IOException {
        language.close();
        // Files.deleteIfExists(Paths.get("temp.bf"));
    }
}
