package interpreter;

import java.util.Deque;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
abstract class Argument implements Option {
    private String name;

    Argument(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean match(Deque<String> commandline) {
        if (name == null || name.equals(commandline.peekFirst()))
            return false;
        commandline.removeFirst();
        return true;
    }
}
