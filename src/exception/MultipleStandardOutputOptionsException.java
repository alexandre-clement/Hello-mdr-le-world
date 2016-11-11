package exception;

/**
 * @author Alexandre Clement
 *         Created the 11/11/2016.
 */
public class MultipleStandardOutputOptionsException extends ExitException {

    public MultipleStandardOutputOptionsException() {
        super(127, "Multiple standard output options.");
    }
}
