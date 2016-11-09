package main;

import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 09/11/2016.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(new Interpreter().build(args).getOptionSnapshot());
    }
}
