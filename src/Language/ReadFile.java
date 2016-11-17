package Language;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public interface ReadFile {
    String next() throws IOException;
    void close() throws IOException;
}
