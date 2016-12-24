package exception;

/**
 * Exception lorsque le programme est mal formé
 *
 * @author Alexandre Clement
 * @see ExitException
 * @since 18/11/2016.
 */
public class NotWellFormedException extends ExitException
{
    public NotWellFormedException(String sourceClass, String sourceMethod, int brace)
    {
        super(4, sourceClass, sourceMethod, "Not well Formed program at instruction " + brace);
    }
}
