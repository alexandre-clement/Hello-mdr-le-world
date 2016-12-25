package language;

import exception.ExitException;

/**
 * Lit le fichier source.
 *
 * @author Alexandre Clement
 * @see BitmapImage
 * @see TextFile
 * @since 17/11/2016.
 */
public interface ReadFile
{
    /**
     * Renvoie le prochain element contenue dans le fichier.
     *
     * @return le prochain element du fichier
     * @throws ExitException si le fichier n'est pas conforme au specification ou si le fichier rencontre une erreur a la lecture
     */
    String next() throws ExitException;

    /**
     * Ferme le reader.
     *
     * @throws ExitException si le fichier rencontre une erreur a la lecture
     */
    void close() throws ExitException;
}
