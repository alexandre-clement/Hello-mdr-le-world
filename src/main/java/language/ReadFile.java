package language;

import exception.ExitException;

/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 *         <p>
 *         Lit le fichier source
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
