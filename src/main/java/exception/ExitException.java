package exception;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 *         <p>
 *         Exception comportant un exit code
 */
public class ExitException extends Exception
{
    private final int exit;
    private final String sourceClass;
    private final String sourceMethod;

    public ExitException(int exit, String sourceClass, String sourceMethod, String message)
    {
        super(message);
        this.exit = exit;
        this.sourceClass = sourceClass;
        this.sourceMethod = sourceMethod;
    }

    public int getExit()
    {
        return exit;
    }

    public String getSourceClass()
    {
        return sourceClass;
    }

    public String getSourceMethod()
    {
        return sourceMethod;
    }
}
