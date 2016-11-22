package Language;

import core.Core;
import core.Instructions;
import exception.ExitException;
import exception.LanguageException;
import interpreter.Flag;
import interpreter.Interpreter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class Language {

    private static final String COMMENT = "#";

    private final OutputStreamWriter stream = new OutputStreamWriter(System.out);
    private Interpreter interpreter;
    private String filename;
    private ReadFile file;
    private Writer output;
    private Reader input;
    private boolean deleteBackLineChar;


    public Language(Interpreter interpreter) throws LanguageException {
        this.interpreter = interpreter;

        try {
            filename = interpreter.getOptionValue(Flag.PRINT);
            String extension = filename.substring(filename.lastIndexOf('.'));
            FileType type = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(extension)).findFirst().get();

            switch (type) {
                case BF:
                    file = new BrainfuckFile(filename);
                    break;
                case BMP:
                    file = new BitmapImage(filename);
                    break;
            }
        } catch (IOException exception) {
            throw new LanguageException(127, exception.getMessage());
        } catch (NoSuchElementException | StringIndexOutOfBoundsException exception) {
            throw new LanguageException(127, "Not a Brainfuck file");
        }

        try {
            if (interpreter.hasOption(Flag.INPUT))
                input = new FileReader(interpreter.getOptionValue(Flag.INPUT));
            else {
                input = new InputStreamReader(System.in);
                deleteBackLineChar = true;
            }
            if (interpreter.hasOption(Flag.OUTPUT))
                output = new FileWriter(interpreter.getOptionValue(Flag.OUTPUT));
            else
                output = stream;
        } catch (IOException exception) {
            throw new LanguageException(3, exception.getMessage());
        }
    }

    public void call(Core core) throws ExitException {
        Deque<Flag> flags = interpreter.getOptions();
        do {
            switch (flags.poll()) {
                case PRINT:
                    core.print();
                    break;
                case REWRITE:
                    core.rewrite();
                    break;
                case TRANSLATE:
                    core.translate();
                    break;
                case CHECK:
                    core.check();
            }
        } while (!flags.isEmpty());
    }

    public int read() throws  LanguageException {
        try {
            int value = input.read();
            if (deleteBackLineChar && value != 10)
                input.read();
            return value;
        } catch (IOException exception) {
            throw new LanguageException(3, "Input file not found");
        }
    }

    public void write(int value) throws LanguageException {
        try {
            output.write(value);
            output.flush();
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

    public void imageOutput(int[] colorArray, int size) {
        try {
            BitmapImage.createImage(filename.substring(0, filename.indexOf(".")), colorArray, size);
        } catch (IOException exception) {
            System.err.println("this should never happen");
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
        StringBuilder stringBuilder = new StringBuilder(COMMENT);
        for (Pattern pattern : patterns) {
            stringBuilder.append("|");
            stringBuilder.append(pattern.pattern());
        }

        Pattern pattern = Pattern.compile(stringBuilder.toString());
        Matcher matcher;
        int length = 0;

        try {
            for (String line = file.next(); line != null; line = file.next())
            {
                matcher = pattern.matcher(line);
                while (matcher.find())
                {
                    if ("#".equals(matcher.group()))
                        break;

                    for (int i = 0; i < instructions.length; i++)
                        if (matcher.group(i + 1) != null)
                        {
                            length += 1;
                            instructionsDeque.add(instructions[i]);
                        }
                }
            }
            file.close();
        } catch (IOException exception) {
            throw new LanguageException(127, "File not found");
        }
        return instructionsDeque.toArray(new Instructions[length]);
    }
}
