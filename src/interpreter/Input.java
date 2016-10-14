package interpreter;

import file.StandardTextFile;

import java.util.Scanner;

/**
 * @author SmartCoding
 *         Created the 14 octobre 2016.
 */
public class Input {
    private Scanner scanner = new Scanner(System.in);

    public void setFile(StandardTextFile file) {
        scanner = new Scanner(file.read());
    }
    public char input() { return (char) Integer.parseInt(scanner.nextLine()); }
}
