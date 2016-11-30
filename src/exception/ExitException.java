package exception;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 */
public class ExitException extends Exception
{
    private int exit;

    public ExitException(int exit, String message)
    {
        super(message);
        this.exit = exit;
    }

    public int getExit()
    {
        return exit;
    }
}
