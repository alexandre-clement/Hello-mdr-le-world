package exception;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 */
public class IllegalCommandlineOptionsException extends ExitException {

    public IllegalCommandlineOptionsException(Exception exception) {
        this(exception.getMessage());
    }

    public IllegalCommandlineOptionsException(String message) {
        this(126, message);
    }

    public IllegalCommandlineOptionsException(int exit, String message) {
        super(exit, message);
    }
}
