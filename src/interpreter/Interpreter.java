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
 * Applies options to the file
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Interpreter {
    /**
     * a List with the options ordered by priority of the option (from the max to the min priority option)
     */
    public final static List<ClassicOption> options = new ArrayList<>(Arrays.asList(
            /* new Check(), */ new InOption(), new OutOption(), new Rewrite(), new Translate(), new Print()));
    /**
     * a Map with the supported files
     */
    private final static Map<String, BrainfuckFile> files = new HashMap<>();
    static {
        files.put(".bf", new Bf());
        files.put(".bmp", new Bmp());
    }

    private String[] commandline; //the input command line

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
            if (files.containsKey(getExtension(arg))) {
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
        if (name == null) return null;
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
        if (files.containsKey(getExtension(name))) {
            BrainfuckFile file = files.get(getExtension(name));
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
    private Object[] readFile(BrainfuckFile bfckFile) {
        return bfckFile.ReadFile();
    }

    /**
     * Add the options found in the commandline to the optionsList
     *
     * @param args the commandline
     * @return a list of options
     */
    private List<BrainfuckOption> findOption(String... args) {
        List<BrainfuckOption> optionsInCommandline = new ArrayList<>(Collections.singletonList(new Check()));
        boolean StdoutOption = false;
        for (ClassicOption option: options) {
            if (option.isIn(args)) {
                if (option instanceof StdoutOption) {
                    if (!StdoutOption) {
                        StdoutOption = true;
                        optionsInCommandline.add(option);
                    } //the second StdoutOption which is neglected to the execution will not be added into the option array
                } else optionsInCommandline.add(option);
            }
        }
        return optionsInCommandline;
    }

    /**
     * Find the filename and the options in the commandline
     * Create the associated brainfuck file to the extensions found in the commandline
     * Read the file just created
     * Call the options on the String version of the file
     *
     * exit code 126 if no option were found
     * exit code 127 if the filename were not found, if the file isn't supported or if the file doesn't exist
     */
    public void buildSystem() {
        List<BrainfuckOption> bfckOptions = findOption(commandline);
        if (bfckOptions.size() == 0) Display.exitCode(126);

        String filename = findFileName(commandline);
        if (filename == null) Display.exitCode(127);

        BrainfuckFile bfckFile = createFile(filename);
        if (bfckFile == null || !bfckFile.isFile()) Display.exitCode(127);

        Object[] objects = readFile(bfckFile);
        if (objects == null) Display.exitCode(127);

        for (BrainfuckOption option : bfckOptions) option.Call(filename, objects);

        Display.exitCode(0);
    }
}
