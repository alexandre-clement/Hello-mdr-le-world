package interpreter;

import exception.InvalidFile;
import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
class Check extends UniqueOption {
    Check() {
        super("--check");
    }

    @Override
    public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
    }
}
