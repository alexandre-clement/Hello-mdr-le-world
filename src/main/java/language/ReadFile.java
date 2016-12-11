package language;

import exception.LanguageException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public interface ReadFile
{
    String next() throws IOException, LanguageException;

    void close() throws IOException;

    void reset() throws IOException;
}
