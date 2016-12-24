package language;

import exception.ExitException;
import interpreter.Flag;
import interpreter.Interpreter;
import main.Main;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * Permet de récupérée les différents flux de l'interpreter
 *
 * @author Alexandre Clement
 * @author TANG Yi
 * @see FileType
 * @see ReadFile
 * @since 26/11/2016.
 */
public class Language
{
    /**
     * Le nom du fichier source
     */
    private String filename;
    /**
     * Le fichier source
     */
    private ReadFile file;
    /**
     * Le flux d'entrée
     */
    private InputStreamReader in;
    /**
     * Le flux de sortie
     */
    private PrintStream out;

    /**
     * Créer les différents flux à partir des données récupérer par l'Interpreter
     *
     * @param interpreter l'interpreter
     * @throws ExitException si l'un des flux n'existe pas / est invalide
     */
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

    /**
     * Retourne le fichier source
     *
     * @return le fichier source
     */
    public ReadFile getFile()
    {
        return file;
    }

    /**
     * Retourne le nom du fichier source
     *
     * @return le nom du fichier source
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * Le flux d'entrée
     *
     * @return le flux d'entrée
     */
    public InputStreamReader getIn()
    {
        return in;
    }

    /**
     * Le flux de sortie
     *
     * @return le flux de sortie
     */
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
            throw new ExitException(127, this.getClass().getSimpleName(), "#getFile", exception);
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
                throw new ExitException(3, this.getClass().getSimpleName(), "#getIn", exception);
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
                throw new ExitException(3, this.getClass().getSimpleName(), "#getOut", exception);
            }
        else
            return new PrintStream(Main.DEFAULT_OUT);
    }

}
