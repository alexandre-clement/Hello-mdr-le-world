package language;

import exception.ExitException;
import interpreter.Flag;
import interpreter.Interpreter;
import main.Main;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

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

    public Language(Interpreter interpreter) throws ExitException
    {
        String pArgument = interpreter.getOptionValue(Flag.PRINT);
        int separator = pArgument.indexOf('.');
        filename = getFilename(pArgument, separator);
        FileType type = getType(pArgument, separator);
        file = getFile(pArgument, type);
        in = getIn(interpreter);
        out = getOut(interpreter);
    }

    public ReadFile getFile()
    {
        return file;
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


    private String getFilename(String pArgument, int separator) throws ExitException
    {
        if (separator == -1)
            throw new ExitException(127, this.getClass().getSimpleName(), "#getFilename", "Illegal filename");
        return pArgument.substring(0, separator);
    }

    private FileType getType(String pArgument, int separator) throws ExitException
    {
        String extension = pArgument.substring(separator);
        Optional<FileType> typeOptional = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(extension)).findFirst();
        if (typeOptional.isPresent())
            return typeOptional.get();
        else
            throw new ExitException(127, this.getClass().getSimpleName(), "#getType", "Not a brainfuck file");
    }

    private ReadFile getFile(String pArgument, FileType type) throws ExitException
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
                    throw new UnsupportedOperationException("File type \"" + type + "\" not implemented yet");
            }
        }
        catch (IOException exception)
        {
            Main.standardException(exception);
            throw new ExitException(127, this.getClass().getSimpleName(), "#getFile", "File not found");
        }
    }

    private InputStreamReader getIn(Interpreter interpreter) throws ExitException
    {
        if (interpreter.hasOption(Flag.INPUT))
            try
            {
                return new InputStreamReader(new FileInputStream(interpreter.getOptionValue(Flag.INPUT)));
            }
            catch (FileNotFoundException exception)
            {
                Main.standardException(exception);
                throw new ExitException(3, this.getClass().getSimpleName(), "#getIn", "In file not found");
            }
        else
            return new InputStreamReader(System.in);
    }

    private PrintStream getOut(Interpreter interpreter) throws ExitException
    {
        if (interpreter.hasOption(Flag.OUTPUT))
            try
            {
                return new PrintStream(interpreter.getOptionValue(Flag.OUTPUT));
            }
            catch (FileNotFoundException exception)
            {
                Main.standardException(exception);
                throw new ExitException(3, this.getClass().getSimpleName(), "#getOut", "Out file not found");
            }
        else
            return new PrintStream(System.out);
    }

}
