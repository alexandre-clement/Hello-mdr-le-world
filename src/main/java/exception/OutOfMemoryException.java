package exception;

/**
 * Exception lorsque le pointeur memoire depasse la capacite memoire.
 *
 * @author Alexandre Clement
 * @see CoreException
 * @since 16/11/2016.
 */
public class OutOfMemoryException extends CoreException
{
    /**
     * Creer une exception lors d'un debordement memoire.
     *
     * @param sourceClass  la classe source
     * @param sourceMethod la methode source
     * @param instruction  l'instruction lorsque l'exception s'est produite
     * @param pointer      la position du pointeur memoire lorsque l'exception s'est produite
     */
    public OutOfMemoryException(String sourceClass, String sourceMethod, int instruction, int pointer)
    {
        super(2, sourceClass, sourceMethod, "Out of memory", instruction, pointer);
    }
}
