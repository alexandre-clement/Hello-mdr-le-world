package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Brainfuck Project
 * Read Class
 *
 * Convert a file to a String
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

class Read {
    private File file;

    Read(File file) { this.file = file; }

    /**
     * read the content of a file and return it as a String
     *
     * @return the content of the file
     */
    String StandardTextFile() {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder(Math.toIntExact(file.length()));
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            return builder.toString();
        } catch (FileNotFoundException exception) { return null; }
    }

    @Override
    public String toString() {
        return file.getPath();
    }
}
