package Language;

import core.Core;
import core.Instructions;
import exception.CoreException;
import exception.ExitException;
import exception.LanguageException;
import interpreter.Flag;
import interpreter.Interpreter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class Language {

    private final OutputStreamWriter stream = new OutputStreamWriter(System.out);
    private Interpreter interpreter;
    private ReadFile file;
    private Writer output;
    private Reader input;


    public Language(Interpreter interpreter) throws LanguageException {
        this.interpreter = interpreter;

        try {
            String filename = interpreter.getOptionValue(Flag.PRINT);
            String extension = filename.substring(filename.lastIndexOf('.'));
            FileType type = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(extension)).findFirst().get();

            switch (type) {
                case Bf:
                    file = new BrainfuckFile(filename);
                    break;
                case Bmp:
                    break;
            }

            if (interpreter.hasOption(Flag.INPUT))
                input = new FileReader(interpreter.getOptionValue(Flag.INPUT));
            else
                input = new InputStreamReader(System.in);

            if (interpreter.hasOption(Flag.OUTPUT))
                output = new FileWriter(interpreter.getOptionValue(Flag.OUTPUT));
            else
                output = stream;

        } catch (IOException exception) {
            throw new LanguageException(127, exception.getMessage());
        }
    }

    public void call(Core core) throws ExitException {
        boolean hasStandardOutputOption = interpreter.hasStandardOutputOption();
        Deque<Flag> flags = interpreter.getOptions();
        while (!flags.isEmpty()) {
            try {
                core.getClass().getMethod(flags.poll().name().toLowerCase()).invoke(core);
            } catch (IllegalAccessException | NoSuchMethodException exception) {
                throw new UnsupportedOperationException("Option not implemented yet");
            } catch (InvocationTargetException exception) {
                throw (ExitException) exception.getCause();
            }
        }
    }

    public int read() throws  LanguageException {
        try {
            return input.read();
        } catch (IOException exception) {
            throw new LanguageException(3, "Input file not found");
        }
    }

    public void write(int value) throws LanguageException {
        try {
            output.write(value);
        } catch (IOException exception) {
            throw new LanguageException(3, "Output file not found");
        }
    }

    public void close() throws LanguageException {
        try {
            file.close();
            input.close();
            output.close();
            stream.close();
        } catch (IOException exception) {
            throw new LanguageException(3, "I/O file not closed");
        }
    }

    public void standardOutput(String string) {
        try {
            stream.write(string);
        } catch (IOException exception) {
            throw new RuntimeException("System.out failure");
        }
    }

    public Instructions[] compile(Instructions[] instructions, Pattern[] patterns) throws LanguageException {
        Deque<Instructions> instructionsDeque = new ArrayDeque<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<patterns.length; i++) {
            stringBuilder.append(patterns[i].pattern());
            if (i != patterns.length - 1)
                stringBuilder.append("|");
        }
        Pattern pattern = Pattern.compile(stringBuilder.toString());
        int length = 0;
        try {
            String line = file.next();
            Matcher matcher;
            Matcher instructionsMatcher;
            while (line != null) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    for (int i=0; i<instructions.length; i++) {
                        instructionsMatcher = patterns[i].matcher(matcher.group(0));
                        if (instructionsMatcher.find()) {
                            length += 1;
                            instructionsDeque.add(instructions[i]);
                        }
                    }
                }
                line = file.next();
                }
            file.close();
        } catch (IOException exception) {
            throw new LanguageException(127, "file not found");
        }
        return instructionsDeque.toArray(new Instructions[length]);
    }
}
