package option;

import java.io.File;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class InOption extends FileOption {

    @Override
    public String getName() {
        return "-i";
    }

    @Override
    public void Call(String filename, Object[] objects) {
        input.setFile(new File(getFilename()));
    }
}
