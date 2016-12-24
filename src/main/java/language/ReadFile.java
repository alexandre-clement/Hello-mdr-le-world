package language;

import exception.ExitException;

/**
 * Lit le fichier source
 *
 * @author Alexandre Clement
 * @see BitmapImage
 * @see TextFile
 * @since 17/11/2016.
 */
public interface ReadFile
{
    /**
     * Renvoie le prochain élément contenue dans le fichier
     *
     * @return le prochain élément du fichier
     * @throws ExitException si le fichier n'est pas conforme au spécification
     */
    String next() throws ExitException;

    /**
     * ferme le reader
     */
    void close() throws ExitException;

    /**
     * reset le reader
     */
    void reset() throws ExitException;
}
