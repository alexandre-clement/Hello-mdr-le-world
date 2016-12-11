package exception;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class LanguageException extends ExitException
{
    public LanguageException(int exit, String message)
    {
        super(exit, message);
    }
}
