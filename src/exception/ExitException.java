package exception;

/**
 * @author Alexandre Clement
 *         Created the 10/11/2016.
 */
public class ExitException extends Exception {
    private int exit;

    public ExitException(int exit, String message) {
        super(message);
        this.exit = exit;
    }

    public ExitException(int exit, Exception exception) {
        this(exit, exception.getMessage());
    }

    public int getExit() {
        return exit;
    }
}
