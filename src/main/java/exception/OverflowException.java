package exception;


/**
 * Exception lorsque la valeur d'une cellule memoire sort de son domaine.
 *
 * @author Alexandre Clement
 * @see CoreException
 * @since 16/11/2016.
 */
public class OverflowException extends CoreException
{
    /**
     * Creer une exception lors d'un overflow.
     *
     * @param sourceClass  la classe source
     * @param sourceMethod la methode source
     * @param instruction  l'instruction lorsque l'exception s'est produite
     * @param pointer      la position du pointeur memoire lorsque l'exception s'est produite
     */
    public OverflowException(String sourceClass, String sourceMethod, int instruction, int pointer)
    {
        super(1, sourceClass, sourceMethod, "Overflow", instruction, pointer);
    }
}
