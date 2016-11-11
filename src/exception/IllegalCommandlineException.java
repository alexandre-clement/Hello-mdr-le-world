package exception;

import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 */
public class IllegalCommandlineException extends ExitException {

    public IllegalCommandlineException(Exception exception) {
        this(exception.getMessage());
    }

    public IllegalCommandlineException(String message) {
        this(126, message);
    }

    public IllegalCommandlineException(int exit, String message) {
        super(exit, message);
    }
}
