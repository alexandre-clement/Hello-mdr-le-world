package exception;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 *         <p>
 *         Exception lorsque le pointeur mémoire dépasse la capacité mémoire
 */
public class OutOfMemoryException extends CoreException
{
    public OutOfMemoryException(String sourceClass, String sourceMethod, int instructions, int pointer)
    {
        super(2, sourceClass, sourceMethod, "Out of memory", instructions, pointer);
    }
}
