package language;

import core.Core;
import core.ExecutionContext;
import core.Instructions;
import exception.LanguageException;
import exception.NotWellFormedException;
import instructions.Executable;
import instructions.Loop;
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
    public static final String COMMENT = "#";
    private String filename;
    private ReadFile file;
    private InputStreamReader in;
    private PrintStream out;

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
    private Pattern compile(Executable[] executables)
    {
        StringBuilder stringBuilder = new StringBuilder("["+COMMENT+"]");
        for (Executable executable : executables)
        {
            stringBuilder.append("|");
            stringBuilder.append(executable.getInstructions().getPattern());
        }
        return Pattern.compile(stringBuilder.toString());
    }

    /**
     * On enregistre les instructions contenues dans le fichier source
     * @return les Instructions du programme
     * @throws LanguageException si le fichier n'existe pas
     */
    public ExecutionContext getExecutionContext() throws LanguageException, NotWellFormedException
    {
        HashMap<Integer, Integer> jumpTable = new HashMap<>();
        Deque<Executable> program = new ArrayDeque<>();
        Executable[] executables = Core.getExecutables();
        List<Deque<Integer>> loops = new ArrayList<>();
        for (int i = 0; i < Instructions.LoopType.values().length; i++)
        {
            loops.add(new ArrayDeque<>());
        }
        Pattern pattern = compile(executables);
        Matcher matcher;

        int length = 0;

        try
        {
            for (String line = file.next(); line != null; line = file.next())
            {
                matcher = pattern.matcher(line);
                while (matcher.find())
                {
                    if (COMMENT.equals(matcher.group()))
                        break;

                    for (int i = 0; i < executables.length; i++)
                        if (matcher.group(i + 1) != null)
                            addExecutable(program, loops, jumpTable, executables[i], length++);
                }
            }
            file.close();
        }
        catch (IOException exception)
        {
            throw new LanguageException(127, "File not found");
        }
        return new ExecutionContext(program.toArray(new Executable[length]), jumpTable, in, out);
    }

    private void addExecutable(Deque<Executable> program, List<Deque<Integer>> loops, HashMap<Integer, Integer> jumpTable, Executable executable, int length) throws NotWellFormedException
    {
        program.add(executable);
        Instructions instructions = executable.getInstructions();
        if (instructions.getLoopType() != null)
            addLoop(loops, jumpTable, executable, length);
    }

    private void addLoop(List<Deque<Integer>> loops, HashMap<Integer, Integer> jumpTable, Executable executable, int length) throws NotWellFormedException
    {
        Loop loop = (Loop) executable;
        Instructions instructions = executable.getInstructions();
        int ordinal = instructions.getLoopType().ordinal();
        if (loop.open())
        {
            loops.get(ordinal).addLast(length);
        }
        else if (!loops.get(ordinal).isEmpty())
        {
            jumpTable.put(loops.get(ordinal).peekLast(), length);
            jumpTable.put(length, loops.get(ordinal).pollLast());
        }
        else
            throw new NotWellFormedException(length);
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
