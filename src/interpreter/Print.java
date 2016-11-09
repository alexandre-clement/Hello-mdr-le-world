package interpreter;

import exception.InvalidFile;
import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.io.IOException;
import java.util.Deque;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
class Print extends Argument {
    private String name;
    Print() {
        super("-p");
    }

    @Override
    public boolean match(Deque<String> commandline) {
        if (!super.match(commandline))
            return false;
        Filenames.source.setName(commandline.pollFirst());
        return true;
    }

    @Override
    public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
    }
}
