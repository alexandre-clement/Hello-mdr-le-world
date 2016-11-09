package interpreter;

import exception.InvalidFile;

import java.util.Deque;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
class InFile extends Argument {
    InFile() {
        super("-i");
    }

    @Override
    public boolean match(Deque<String> commandline) {
        if (!super.match(commandline))
            return false;
        Filenames.input.setName(commandline.pollFirst());
        return true;
    }

    @Override
    String getName() {
        return super.getName();
    }

    @Override
    public void call() throws InvalidFile {
    }
}
