package language;

import main.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 *         <p>
 *         Lit les fichiers textes brainfuck
 */
public class TextFile implements ReadFile
{
    private final RandomAccessFile source;

    /**
     * @param filename le nom du fichier
     * @throws FileNotFoundException si le fichier n'est pas trouver
     */
    TextFile(String filename) throws FileNotFoundException
    {
        source = new RandomAccessFile(filename, "r");
    }

    /**
     * @return la prochaine ligne du fichier
     */
    @Override
    public String next()
    {
        try
        {
            return source.readLine();
        }
        catch (IOException e)
        {
            Main.standardException(e);
        }
        return null;
    }

    /**
     * Retourne à la première ligne
     */
    @Override
    public void reset()
    {
        try
        {
            source.seek(0);
        }
        catch (IOException e)
        {
            Main.standardException(e);
        }
    }

    /**
     * Ferme le reader
     */
    @Override
    public void close()
    {
        try
        {
            source.close();
        }
        catch (IOException e)
        {
            Main.standardException(e);
        }
    }
}
