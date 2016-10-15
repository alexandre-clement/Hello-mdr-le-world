package option;

import file.StandardTextFile;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class OutOption extends FileOption {

    @Override
    public String getName() {
        return "-o";
    }

    @Override
    public void Call(String filename, String program) {
        display.setFile(new StandardTextFile(getFilename()));
    }
}
