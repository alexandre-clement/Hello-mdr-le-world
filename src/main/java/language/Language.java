package language;

import core.ExecutionContext;
import core.Instructions;
import exception.LanguageException;
import exception.NotWellFormedException;
import interpreter.Flag;
import interpreter.Interpreter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 * @author TANG Yi
 *         Created the 26/11/2016.
 */
public class Language
{
    private static final String COMMENT = "#";
    private String filename;
    private ReadFile file;
    private InputStreamReader in;
    private PrintStream out;

    private Map<Integer, Integer> jumptable;

    public Language(Interpreter interpreter) throws LanguageException
    {
        String pArgument = interpreter.getOptionValue(Flag.PRINT);
        int separator = pArgument.indexOf('.');
        filename = getFilename(pArgument, separator);
        FileType type = getType(pArgument, separator);
        file = getFile(pArgument, type);
        in = getIn(interpreter);
        out = getOut(interpreter);
    }

    public String getFilename()
    {
        return filename;
    }

    public InputStreamReader getIn()
    {
        return in;
    }

    public PrintStream getOut()
    {
        return out;
    }

    /**
     * Compile tous les paternes ensemble et rajouter les commentaires i.e #
     * @return le pattern somme de tous les paternes d'instructions
     */
    private Pattern compile() {
        StringBuilder stringBuilder = new StringBuilder(COMMENT);
        for (Instructions instructions : Instructions.values()) {
            stringBuilder.append("|");
            stringBuilder.append(instructions.getPattern());
        }
        return Pattern.compile(stringBuilder.toString());
    }

    /**
     * On enregistre les instructions contenues dans le fichier source
     * @return les Instructions du programme
     * @throws LanguageException si le fichier n'existe pas
     */
    public ExecutionContext getExcecutionContext() throws LanguageException, NotWellFormedException {
        jumptable = new HashMap<>();
        Deque<Instructions> instructionsDeque = new ArrayDeque<>();
        Pattern pattern = compile();
        Matcher matcher;
        Instructions[] instructions = Instructions.values();
        int length = 0;
        int jump = 0;
        int brace;

        try
        {
            for (String line = file.next(); line != null; line = file.next())
            {
                matcher = pattern.matcher(line);
                while (matcher.find())
                {
                    if (COMMENT.equals(matcher.group()))
                        break;

                    for (int i = 0; i < instructions.length; i++)
                        if (matcher.group(i + 1) != null)
                        {
                            length += 1;
                            brace = instructionsDeque.size()-1;
                            instructionsDeque.add(instructions[i]);
                            if (instructions[i] == Instructions.JUMP)
                                jumptable.put(jump++, brace);
                            if (instructions[i] == Instructions.BACK)
                            {
                                jumptable.put(brace, jumptable.get(--jump));
                                jumptable.put(jumptable.get(jump), brace);
                                if (!jumptable.containsKey(jump))
                                    throw new NotWellFormedException(brace);
                                if (jumptable.get(jump) != jump)
                                    jumptable.remove(jump);
                            }
                        }
                }
            }
            file.close();
        }
        catch (IOException exception)
        {
            throw new LanguageException(127, "File not found");
        }
        return new ExecutionContext(instructionsDeque.toArray(new Instructions[length]), jumptable, in, out);
    }

    private String getFilename(String pArgument, int separator) throws LanguageException
    {
        if (separator == -1)
            throw new LanguageException(127, "Illegal filename");
        return pArgument.substring(0, separator);
    }

    private FileType getType(String pArgument, int separator) throws LanguageException
    {
        String extension = pArgument.substring(separator);
        Optional<FileType> typeOptional = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(extension)).findFirst();
        if (typeOptional.isPresent())
            return typeOptional.get();
        else
            throw new LanguageException(127, "Not a brainfuck file");
    }

    private ReadFile getFile(String pArgument, FileType type) throws LanguageException
    {
        try
        {
            switch (type)
            {
                case BF:
                    return new TextFile(pArgument);
                case BMP:
                    return new BitmapImage(pArgument);
                default:
                    throw new UnsupportedOperationException("File type not implemented yet");
            }
        }
        catch (IOException exception)
        {
            throw new LanguageException(127, "File not found");
        }
    }

    private InputStreamReader getIn(Interpreter interpreter) throws LanguageException
    {
        if (interpreter.hasOption(Flag.INPUT))
            try
            {
                return new InputStreamReader(new FileInputStream(interpreter.getOptionValue(Flag.INPUT)));
            }
            catch (FileNotFoundException exception)
            {
                throw new LanguageException(3, "In file not found");
            }
        else
            return new InputStreamReader(System.in);
    }

    private PrintStream getOut(Interpreter interpreter) throws LanguageException
    {
        if (interpreter.hasOption(Flag.OUTPUT))
            try
            {
                return new PrintStream(interpreter.getOptionValue(Flag.OUTPUT));
            }
            catch (FileNotFoundException exception)
            {
                throw new LanguageException(3, "Out file not found");
            }
        else
            return new PrintStream(System.out);
    }

}
