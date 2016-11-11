package exception;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 */
public class IllegalCommandlineOptionsException extends ExitException {

    public IllegalCommandlineOptionsException(Exception exception) {
        this(exception.getMessage());
    }

    public IllegalCommandlineOptionsException(ExitException exception) {
        super(exception.getExit(), exception.getMessage());
    }

    public IllegalCommandlineOptionsException(String message) {
        super(126, message);
    }
}
