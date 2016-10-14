package file;

import interpreter.Display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * Brainfuck Project
 *
 * Convert a file to a String
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class StandardTextFile {
    private File file;

    public StandardTextFile(String name) {
        if (name == null) file=null;
        else file = new File(name);
    }

    StandardTextFile(File file) { this.file = file; }

    /**
     * read the content of a file and return it as a String
     *
     * @return the content of the file
     */
    String read() {
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

    public void write(char character) {
        try {
            Files.write(Paths.get(file.getPath()), String.valueOf(character).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            Display.exitCode(3);
        }
    }

    @Override
    public String toString() {
        return file.getPath();
    }
}
