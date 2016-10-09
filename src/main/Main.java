package main;

import interpreter.Interpreter;


/**
 * Brainfuck Project
 * Main Class
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Main {
    public static void main(String... args) {
        new Interpreter(args).buildSystem();
    }
}
