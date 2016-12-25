package exception;

/**
 * Exception lorsque le programme est mal forme.
 *
 * @author Alexandre Clement
 * @see ExitException
 * @since 18/11/2016.
 */
public class NotWellFormedException extends ExitException
{
    /**
     * Creer une exception lorsque le programme est mal forme.
     *
     * @param sourceClass  la classe source
     * @param sourceMethod la methode source
     * @param brace        l'instruction qui pose un probleme de formation
     */
    public NotWellFormedException(String sourceClass, String sourceMethod, int brace)
    {
        super(4, sourceClass, sourceMethod, "Not well Formed program at instruction " + brace);
    }
}
