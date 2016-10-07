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

public class Bf extends BfckFile{

    public Bf() { super(); }

    @Override
    public String ReadFile() {
        Read read = new Read(super.getFile());
        return read.StandardTextFile();
    }

    @Override
    public void setFile(String name) { super.setFile(name); }
}
