package exception;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class CoreException extends ExitException {
    public CoreException(int exit, String message) {
        super(exit, message);
    }
    public CoreException(int exit, String message, int instructions, int pointer) {
        this(exit, message + " at instructions " + instructions + ", memory pointer was at " + pointer);
    }
}
