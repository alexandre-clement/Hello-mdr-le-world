package exception;

import core.Core;

/**
 * @author Alexandre Clement
 *         Created the 18/11/2016.
 */
public class NotWellFormedException extends CoreException {
    public NotWellFormedException() {
        super(4, "Not well Formed program");
    }

    public NotWellFormedException(int brace) {
        super(4, "Not well Formed program at instruction " + brace);
    }
}
