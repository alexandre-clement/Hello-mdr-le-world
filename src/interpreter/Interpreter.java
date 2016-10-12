package interpreter;

import option.*;
import file.*;

import java.util.*;

/**
 * Brainfuck Project
 * Interpreter Class
 *
 * Take a commandline of the shell
 * Find and get the options and the brainfuck file
 * Applies macro to the file
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Interpreter {
    private final static Map<String, BrainfuckOption> options = new HashMap<>();
    private final static Map<String, BrainfuckFile> extensions = new HashMap<>();

    static {
        // Initialize a map with <extensions of supported files, name of the associated Class>
        extensions.put(".bf", new Bf());
        extensions.put(".bmp", new Bmp());

        // Initialize a map with <name of options, name of the associated Class>
        options.put("-p", new Print());
        options.put("--rewrite", new Rewrite());
    }

    private String[] commandline;

    public Interpreter(String... commandline) {
        this.commandline = commandline;
    }

    /**
     * Find, if it exists, the name of the file in the arguments. i.e a String which have an extension
     *
     * @param args the arguments given in the commandline
     * @return the name of the file with its extension
     */
    private String findFileName(String... args) {
        for (String arg : args) {
            if (extensions.containsKey(getExtension(arg))) {
                return arg;
            }
        }
        return null;
    }

    /**
     * Find and return the extension of a String
     *
     * @param name the filename
     * @return the extension of the String
     */
    private String getExtension(String name) {
        int index = name.lastIndexOf(".");
        if (index > 0) return name.substring(index);
        return null;
    }

    /**
     * Create the file with the name
     *
     * @param name the filename
     * @return a new brainfuck file
     */
    private BrainfuckFile createFile(String name) {
        if (name == null) return null;
        if (extensions.containsKey(getExtension(name))) {
            BrainfuckFile file = extensions.get(getExtension(name));
            file.setFile(name);
            return file;
        }
        else return null;
    }

    /**
     * Convert a file content to a string
     * @param bfckFile the file just created
     * @return the file content
     */
    private String readFile(BrainfuckFile bfckFile) {
        return bfckFile.ReadFile();
    }

    /**
     * Add the options found in the commandline to the optionsList
     *
     * @param args the commandline
     * @return a list of options
     */
    private List<BrainfuckOption> findOption(String... args) {
        List<BrainfuckOption> optionsList = new ArrayList<>();
        for (String arg: args) {
            if (options.containsKey(arg)) {
                optionsList.add(options.get(arg));
            }
        }
        return optionsList;
    }

    /**
     * Find the filename and the options in the commandline
     * Create the associated brainfuck file to the extensions found in the commandline
     * Read the file just created
     * Call the options on the String version of the file
     *
     */
    public void buildSystem() {
        List<BrainfuckOption> bfckOptions = findOption(commandline);
        if (bfckOptions.size() == 0) Display.exitCode(126);

        String filename = findFileName(commandline);
        if (filename == null) Display.exitCode(127);

        BrainfuckFile bfckFile = createFile(filename);
        if (bfckFile == null || !bfckFile.isFile()) Display.exitCode(127);

        String program = readFile(bfckFile);
        if (program == null) Display.exitCode(127);

        for (BrainfuckOption option : bfckOptions) option.Call(program);

        Display.exitCode(0);
    }
}
