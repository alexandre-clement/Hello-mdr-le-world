package Language;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public interface ReadFile {
    String next() throws IOException;
    void close() throws IOException;
}
