package option;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
abstract class FileOption extends BrainfuckOption {
    private String filename;

    @Override
    public boolean isIn(String... commandline) {
        for (int i=0; i < commandline.length; i++) {
            if (getName().equals(commandline[i])) {
                if (i+1 < commandline.length) {
                    filename = commandline[i + 1];
                    commandline[i + 1] = null;
                    return true;
                }
            }
        }
        return false;
    }

    public String getFilename() {
        return filename;
    }
}

