package interpreter;

/**
 * Brainfuck Project
 * Display Static Class
 *
 * Print on standard output an array of String
 * Print error
 * Print exit code
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Display {

    /**
     * @param output the content to display on the standard output
     */
    public static void display(Object... output) {
        for (Object out : output) {
            System.out.println(out);
        }
    }

    /**
     * @param exitCode the exit code of the program
     */
    public static void exitCode(int exitCode) {
        //Display.display("Exit code " + exitCode);
        System.exit(exitCode);
    }
}
