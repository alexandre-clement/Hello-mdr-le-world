package option;

/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public class In extends FileOption {

    @Override
    public String getName() {
        return "-i";
    }

    @Override
    public void Call(String program) {
        language.setInput();
    }
}
