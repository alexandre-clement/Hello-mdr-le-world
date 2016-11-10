package main;

import exception.ExitException;
import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
public class Main {
    public static void main(String[] args) {
        int exit = 0;
        try {
            new Interpreter().build(args).;
        } catch (ExitException exception) {
            System.err.println(exception.getMessage());
            exit = exception.getExit();
        }
        System.exit(exit);
    }
}
