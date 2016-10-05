package interpreter;

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
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public class Display {
    public static void display(String... output) {
        for (String out : output) {
            System.out.print(out);
        }
    }

    static void ExitCode(int exitCode) {
        Display.display("Exit code " + exitCode);
        System.exit(exitCode);
    }
}
