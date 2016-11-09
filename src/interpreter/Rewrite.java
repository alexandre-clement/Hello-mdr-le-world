package interpreter;

import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
class Rewrite extends UniqueOption {
    Rewrite() {
        super("--rewrite");
    }

    @Override
    public void call() throws IOException, OverflowException, OutOfMemoryException, MalFormedException {
    }
}
