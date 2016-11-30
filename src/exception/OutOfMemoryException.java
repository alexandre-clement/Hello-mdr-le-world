package exception;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class OutOfMemoryException extends CoreException
{
    public OutOfMemoryException(int instructions, int pointer)
    {
        super(2, "Out of memory", instructions, pointer);
    }
}
