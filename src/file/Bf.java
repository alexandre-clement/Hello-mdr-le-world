package file;

/**
 * Brainfuck Project
 * Bf Class
 *
 * The Class associated to .Bf file
 * .Bf are standard text file
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Bf extends BrainfuckFile {

    public Bf() { super(); }

    /**
     * @return the content of the file as a String
     */
    @Override
    public String ReadFile() {
        StandardTextFile standardTextFile = new StandardTextFile(super.getFile());
        return standardTextFile.read();
    }

    @Override
    public void setFile(String name) { super.setFile(name); }
}
