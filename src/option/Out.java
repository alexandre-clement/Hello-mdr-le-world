package option;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class Out extends FileOption {

    @Override
    public String getName() {
        return "-o";
    }

    @Override
    public void Call(String program) {
        language.setOutput(getFilename());
    }
}
