package exception;


/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 *         <p>
 *         Exception lorsque la valeur d'une cellule mémoire sort de son domaine
 */
public class OverflowException extends CoreException
{
    public OverflowException(String sourceClass, String sourceMethod, int instruction, int pointer)
    {
        super(1, sourceClass, sourceMethod, "Overflow", instruction, pointer);
    }
}
