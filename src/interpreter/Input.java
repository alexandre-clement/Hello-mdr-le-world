package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author SmartCoding
 *         Created the 14 octobre 2016.
 */
public class Input {
    private Scanner scanner = new Scanner(System.in);

    public void setFile(File file) {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            Display.exitCode(3);
        }
    }
    public char input() { return (char) Integer.parseInt(scanner.nextLine()); }
}
