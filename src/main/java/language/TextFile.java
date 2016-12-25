package language;

import exception.ExitException;
import macro.MacroBuilder;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Lit les fichiers textes brainfuck en appliquant les macros.
 *
 * @author Alexandre Clement
 * @see ReadFile
 * @since 17/11/2016.
 */
public class TextFile implements ReadFile
{
    private final RandomAccessFile source;

    /**
     * Creer un objet ReadFile permettant de lire et d'executer les macros present dans un fichier texte.
     *
     * @param filename le nom du fichier
     * @throws IOException si le fichier n'est pas trouver
     */
    TextFile(String filename) throws IOException
    {
        source = new RandomAccessFile(new MacroBuilder(filename).build(), "r");
    }

    /**
     * @return la prochaine ligne du fichier
     * @throws ExitException si le fichier rencontre une erreur a la lecture
     */
    @Override
    public String next() throws ExitException
    {
        try
        {
            return source.readLine();
        }
        catch (IOException e)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#next", e);
        }
    }

    /**
     * Ferme le reader.
     *
     * @throws ExitException si le fichier rencontre une erreur a la fermeture
     */
    @Override
    public void close() throws ExitException
    {
        try
        {
            source.close();
        }
        catch (IOException e)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "#close", e);
        }
    }
}
