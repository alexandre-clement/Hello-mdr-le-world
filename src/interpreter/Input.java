package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author SmartCoding
 *         Created the 14 octobre 2016.
 */
public class Input {
    private Scanner scanner;

    public Input() {
        scanner = new Scanner(System.in); //initialized with the standard input
    }

    public void setFile(File file) {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            Display.exitCode(3);
        }
    }
    public char input() {

        if (scanner.hasNext()) {
            String in = scanner.next();
            if (in.length() > 0)
                return in.charAt(0);
            else return input();
        }
        Display.exitCode(3);
        return 0;
    }
}
