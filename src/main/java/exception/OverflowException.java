package exception;


/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class OverflowException extends CoreException
{
    public OverflowException(int instruction, int pointer)
    {
        super(1, "Overflow", instruction, pointer);
    }
}
