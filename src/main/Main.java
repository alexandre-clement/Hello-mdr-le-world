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

    /**
     * The main method
     *
     * @param args: the arguments given in the commandline
     */
    public static void main(String... args) {
        new Interpreter("-p", "test.bf", "--translate").buildSystem();
    }
}
