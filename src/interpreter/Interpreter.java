package interpreter;

import model.Memory;
import option.*;
import file.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private String filename;
    private BfckFile bfckFile;
    private String program;
    private List<BfckOption> bfckOptions = new ArrayList<>();

    public static Memory MEMORY = new Memory();

    public Interpreter(String... commandline) {
        this.commandline = commandline;
        // Initialize a map with <extensions of supported files as a String, name of the associated Class as a String>
        extensions.put(".bf", new Bf());
        extensions.put(".bmp", new Bmp());
        // Initialize a map with <name of options, name of the associated Class as a String>
        options.put("-p", new Print());
        FindFileName(commandline);
        FindOption(commandline);
        if (bfckOptions.size() == 0) Display.ExitCode(126);
    }

    private String FindFileName(String... args) {
        if (args == null) return null;
        for (String arg : args) {
            if (extensions.containsKey(getExtension(arg))) {
                filename = arg;
                return filename;
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

    private BfckFile getFile(String name) {
        if (name == null) return null;
        if (extensions.containsKey(getExtension(name))) {
            bfckFile = extensions.get(getExtension(name));
            bfckFile.setFile(name);
            return bfckFile;
        }
        else return null;
    }

    private String readFile(BfckFile bfckFile) {
        if (bfckFile == null) return null;
        program = bfckFile.ReadFile();
        return program;
    }

    private List<BfckOption> FindOption(String... args) {
        for (String arg: args) {
            if (options.containsKey(arg)) {
                bfckOptions.add(options.get(arg));
            }
        }
        return bfckOptions;
    }

    public void buildSystem() {
        if (commandline == null || commandline.length == 0)  {
            Display.ExitCode(126);
            return;
        }
        if (filename == null) {
            Display.ExitCode(127);
            Display.display(extensions.keySet().toString());
            return;
        }
        getFile(filename);
        if (!bfckFile.isFile()) {
            Display.ExitCode(127);
        }
        readFile(bfckFile);
        if (program == null) {
            Display.ExitCode(127);
            return;
        }
        for (BfckOption option : bfckOptions) {
            option.Call(program);
        }
        Display.ExitCode(0);
    }
}
