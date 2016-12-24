package exception;

/**
 * Exception lors de l'exécution du programme
 *
 * @author Alexandre Clement
 * @see ExitException
 * @since 16/11/2016.
 */
class CoreException extends ExitException
{
    CoreException(int exit, String sourceClass, String sourceMethod, String message, int instructions, int pointer)
    {
        super(exit, sourceClass, sourceMethod, message + " at instructions " + instructions + ", memory pointer was at " + pointer);
    }
}
