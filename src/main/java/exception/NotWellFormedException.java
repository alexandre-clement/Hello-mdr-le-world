package exception;

/**
 * @author Alexandre Clement
 *         Created the 18/11/2016.
 *         <p>
 *         Exception lorsque le programme est mal formé
 */
public class NotWellFormedException extends ExitException
{
    public NotWellFormedException(String sourceClass, String sourceMethod, int brace)
    {
        super(4, sourceClass, sourceMethod, "Not well Formed program at instruction " + brace);
    }
}
