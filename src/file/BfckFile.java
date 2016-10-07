package file;

import java.io.File;

/**
 * Brainfuck Project
 * BfckFile abstract Class
 *
 * Implement a set/get file
 * Implement a ReadFile method which read the given file
 * Override toString
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public abstract class BfckFile {
    private File file;

    BfckFile() {}

    public void setFile(String name) {
        this.file = new File(name);
    }

    File getFile() {
        return file;
    }

    public abstract String ReadFile();

    public boolean isFile() { return file.isFile(); }

    @Override
    public String toString() {
        if (file == null) return "null";
        return file.getAbsolutePath();
    }
}
