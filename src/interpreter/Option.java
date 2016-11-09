package interpreter;

import exception.InvalidFile;
import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
interface Option {
    void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException;
}
