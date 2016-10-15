package file;

import java.io.File;

/**
 * Brainfuck Project
 * BrainfuckFile abstract Class
 *
 * Implement a set/get file
 * Implement a ReadFile method which read the given file
 * Override toString
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public abstract class BrainfuckFile {
    private File file;

    BrainfuckFile() {}

    public void setFile(String name) {
        this.file = new File(name);
    }

    File getFile() {
        return file;
    }

    /**
     * Convert the file content to a String
     */
    public abstract Object[] ReadFile();

    /**
     * Check if the file exists
     */
    public boolean isFile() { return file.isFile(); }

    @Override
    public String toString() {
        if (file == null) return "null";
        return file.getAbsolutePath();
    }
}
