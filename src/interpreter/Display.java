package interpreter;

import file.StandardTextFile;

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
    private StandardTextFile file;

    public void setFile(StandardTextFile file) {
        this.file = file;
    }

    public void out(char character) {
        if (file == null) display(character);
        else file.write(character);
    }

    /**
     * @param output the content to display on the standard output
     */
    public static void display(Object... output) {
        for (Object out : output) {
            System.out.print(out);
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
