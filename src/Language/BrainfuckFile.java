package Language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public class BrainfuckFile implements ReadFile {
    private RandomAccessFile source;

    public BrainfuckFile(String filename) throws FileNotFoundException {
        source = new RandomAccessFile(filename, "r");
    }

    @Override
    public String next() throws IOException {
        return source.readLine();
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}
