package language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public class TextFile implements ReadFile
{
    private RandomAccessFile source;

    TextFile(String filename) throws FileNotFoundException
    {
        source = new RandomAccessFile(filename, "r");
    }

    @Override
    public String next() throws IOException
    {
        return source.readLine();
    }

    @Override
    public void close() throws IOException
    {
        source.close();
    }
}
