package exception;

/**
 * Exception lorsque le pointeur mémoire dépasse la capacité mémoire
 *
 * @author Alexandre Clement
 * @see CoreException
 * @since 16/11/2016.
 */
public class OutOfMemoryException extends CoreException
{
    public OutOfMemoryException(String sourceClass, String sourceMethod, int instructions, int pointer)
    {
        super(2, sourceClass, sourceMethod, "Out of memory", instructions, pointer);
    }
}
