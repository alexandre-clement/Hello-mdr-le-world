package exception;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 *         <p>
 *         Exception lors de l'exécution du programme
 */
class CoreException extends ExitException
{
    CoreException(int exit, String sourceClass, String sourceMethod, String message, int instructions, int pointer)
    {
        super(exit, sourceClass, sourceMethod, message + " at instructions " + instructions + ", memory pointer was at " + pointer);
    }
}
