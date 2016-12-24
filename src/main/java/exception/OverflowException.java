package exception;


/**
 * Exception lorsque la valeur d'une cellule mémoire sort de son domaine
 *
 * @author Alexandre Clement
 * @see CoreException
 * @since 16/11/2016.
 */
public class OverflowException extends CoreException
{
    public OverflowException(String sourceClass, String sourceMethod, int instruction, int pointer)
    {
        super(1, sourceClass, sourceMethod, "Overflow", instruction, pointer);
    }
}
