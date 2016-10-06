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
 *
 * @author SmartCoding
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public class Interpreter {
    private final static Map<String, BfckOption> options = new HashMap<>();
    private final static Map<String, BfckFile> extensions = new HashMap<>();

    private String[] commandline;

    public Interpreter(String... commandline) {
        this.commandline = commandline;
        // Initialize a map with <extensions of supported files, name of the associated Class>
        extensions.put(".bf", new Bf());
        extensions.put(".bmp", new Bmp());

        // Initialize a map with <name of options, name of the associated Class>
        options.put("-p", new Print());
    }

    private String findFileName(String... args) {
        if (args == null) return null;
        for (String arg : args) {
            if (extensions.containsKey(getExtension(arg))) {
                return arg;
            }
        }
        return null;
    }

    private String getExtension(String name) {
        if (name == null) return null;
        int index = name.lastIndexOf(".");
        if (index > 0) return name.substring(index);
        return null;
    }

    private BfckFile createFile(String name) {
        if (name == null) return null;
        if (extensions.containsKey(getExtension(name))) {
            BfckFile file = extensions.get(getExtension(name));
            file.setFile(name);
            return file;
        }
        else return null;
    }

    private String readFile(BfckFile bfckFile) {
        if (bfckFile == null) return null;
        return bfckFile.ReadFile();
    }

    private List<BfckOption> findOption(String... args) {
        List<BfckOption> optionsList = new ArrayList<>();
        for (String arg: args) {
            if (options.containsKey(arg)) {
                optionsList.add(options.get(arg));
            }
        }
        return optionsList;
    }

    public void buildSystem() {
        String filename = findFileName(commandline);
        if (filename == null) Display.ExitCode(127);


        List<BfckOption> bfckOptions = findOption(commandline);
        if (bfckOptions.size() == 0) Display.ExitCode(126);

        BfckFile bfckFile = createFile(filename);
        if (bfckFile == null || !bfckFile.isFile()) Display.ExitCode(127);

        String program = readFile(bfckFile);
        if (program == null) Display.ExitCode(127);

        for (BfckOption option : bfckOptions) option.Call(program);

        Display.ExitCode(0);
    }
}
