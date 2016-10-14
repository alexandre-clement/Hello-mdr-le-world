package option;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
abstract class FileOption extends BrainfuckOption {
    private String filename = null;

    /**
     * Check if the file option is in the command line
     * if it is, set the filename with the next command
     * replace the next command by null, avoiding problem when we will search the program in the command line
     * @param commandline with the option and the file
     * @return true the file option is in the command line, false otherwise
     */
    @Override
    public boolean isIn(String... commandline) {
        for (int i=0; i < commandline.length; i++) {
            if (getName().equals(commandline[i])) {
                if (i+1 < commandline.length) {
                    filename = commandline[i + 1];
                    commandline[i + 1] = null;
                }
                return true;
            }
        }
        return false;
    }

    public String getFilename() {
        return filename;
    }
}

