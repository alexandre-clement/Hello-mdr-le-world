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
    private String temp;

    public Input() {
        scanner = new Scanner(System.in); //initialized with the standard input
        temp = "";
    }

    public void setFile(File file) {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            Display.exitCode(3);
        }
    }
    public char input() {
        if (temp.length() > 0) {
            char input = temp.charAt(0);
            temp = temp.substring(1);
            return input;
        }
        if (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            if (in.length() > 0) {
                temp = in.substring(1);
                return in.charAt(0);
            }
            else return '\n';
        }
        Display.exitCode(3);
        return 0;
    }
}
