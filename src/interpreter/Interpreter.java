package interpreter;

import option.*;
import file.*;

import java.util.*;

/**
 * Brainfuck Project
 * Interpreter Class
 *
 * Take a commandline of the shell
 * Find and get the macros and the brainfuck file
 * Applies macro to the file
 *
 *
 * @author SmartCoding
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public class Interpreter {
    private final static Map<String, String> macros = new HashMap<>();
    private final static Map<String, String> extensions = new HashMap<>();

    private String[] commandline;
    private String filename;
    private BfckFile bfckFile;
    private String program;
    private List<BfckOption> bfckMacros = new ArrayList<>();


    public Interpreter(String... commandline) {
        this.commandline = commandline;
        // Initialize a map with <extensions of supported files as a String, name of the associated Class as a String>
        extensions.put(".Bf", "Bf");
        extensions.put(".Bmp", "Bmp");
        // Initialize a map with <name of options, name of the associated Class as a String>
        macros.put("-p", "print");
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
            try {
                Class cls = Class.forName("File." + extensions.get(getExtension(name)));
                bfckFile = (BfckFile) cls.newInstance();
            } catch (InstantiationException | ClassNotFoundException | IllegalAccessException exception) {
                return null;
            }
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

    private List<BfckOption> FindMacro(String... args) {
        for (String arg: args) {
            if (macros.containsKey(arg)) {
                try {
                    Class cls = Class.forName("Option." + macros.get(arg));
                    bfckMacros.add((BfckOption) cls.newInstance());
                } catch (InstantiationException | ClassNotFoundException | IllegalAccessException exception) {
                    return null;
                }
            }
        }
        return bfckMacros;
    }

    public void buildSystem() {
        if (commandline == null || commandline.length == 0)  {
            Display.ExitCode(400);
            return;
        }
        FindFileName(commandline);
        FindMacro(commandline);
        if (filename == null) {
            Display.ExitCode(401);
            Display.display(extensions.keySet().toString());
            return;
        }
        getFile(filename);
        if (!bfckFile.isFile()) {
            Display.ExitCode(402);
        }
        readFile(bfckFile);
        if (program == null) {
            Display.ExitCode(403);
            return;
        }
        for (BfckOption macro : bfckMacros) {
            macro.Call(program);
        }
        // execute the program if the user do not give any macro
        if (bfckMacros.size() == 0) Display.display(program, "\n");
        Display.ExitCode(0);
    }
}
