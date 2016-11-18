package exception;

/**
 * @author Alexandre Clement
 *         Created the 18/11/2016.
 */
public class NotWellFormedException extends ExitException {
    public NotWellFormedException() {
        super(4, "Not well Formed program");
    }
}
