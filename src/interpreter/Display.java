package interpreter;

import static interpreter.Interpreter.MEMORY;

/**
 * Brainfuck Project
 * Display Static Class
 *
 * Print on standard output an array of String
 * Print error
 * Print exit code
 *
 *
 * @author SmartCoding
 * @version Slice 0+
 *
 * Created the 4 October 2016
 */

public class Display {
    static void display(String... output) {
        for (String out : output) {
            System.out.println(out);
        }
    }

    /**
     * print the contents of memory (relevant)
     */
    public static void displayMemory() {
        System.out.println(MEMORY.toString());
    }

    static void ExitCode(int exitCode) {
        Display.display("Exit code " + exitCode);
        System.exit(exitCode);
    }
}
