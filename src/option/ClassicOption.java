package option;

/**
 * @author SmartCoding
 *         Created the 16 octobre 2016.
 */
public abstract class ClassicOption implements BrainfuckOption{

    /**
     * @return the name of the option
     */
    public abstract String getName();

    /**
     * Check if the option is in the commandline
     * @param commandline with the option and the file
     * @return true if the option is in the commandline, false otherwise
     */
    public boolean isIn(String... commandline) {
        for (String command: commandline) {
            if (getName().equals(command)) return true;
        }
        return false;
    }
}
