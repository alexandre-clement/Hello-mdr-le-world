package language;

import exception.ExitException;
import interpreter.Flag;
import interpreter.Interpreter;
import main.Main;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * Permet de recuperee les differents flux de l'interpreter.
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
     * Le nom du fichier source.
     */
    private String filename;
    /**
     * Le fichier source.
     */
    private ReadFile file;
    /**
     * Le flux d'entree.
     */
    private InputStream in;
    /**
     * Le flux de sortie.
     */
    private PrintStream out;

    /**
     * Creer les differents flux a partir des donnees recuperer par l'Interpreter.
     *
     * @param interpreter l'interpreter
     * @throws ExitException si l'un des flux n'existe pas / est invalide
     */
    public Language(Interpreter interpreter) throws ExitException
    {
        String pArgument = interpreter.getOptionValue(Flag.PRINT);
        int separator = pArgument.lastIndexOf('.');
        filename = getFilename(pArgument, separator);
        FileType type = getType(pArgument, separator);
        file = getFile(pArgument, type);
        in = getIn(interpreter);
        out = getOut(interpreter);
    }

    /**
     * Retourne le fichier source.
     *
     * @return le fichier source
     */
    public ReadFile getFile()
    {
        return file;
    }

    /**
     * Retourne le nom du fichier source.
     *
     * @return le nom du fichier source
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * Le flux d'entree.
     *
     * @return le flux d'entree
     */
    public InputStream getIn()
    {
        return in;
    }

    /**
     * Le flux de sortie.
     *
     * @return le flux de sortie
     */
    public PrintStream getOut()
    {
        return out;
    }

    /**
     * Recupere le nom de fichier sans l'extension.
     *
     * @param pArgument le nom complet du fichier
     * @param separator la position du point séparant le nom du fichier de son extension
     * @return le nom du fichier
     * @throws ExitException si le fichier n'a pas d'extension
     */
    private String getFilename(String pArgument, int separator) throws ExitException
    {
        if (separator == -1)
            throw new ExitException(127, this.getClass().getSimpleName(), "#getFilename", "Illegal filename");
        return pArgument.substring(0, separator);
    }

    /**
     * Recupere le type du fichier source en fonction de son extension.
     *
     * @param pArgument le nom complet du fichier
     * @param separator la position du point séparant le nom du fichier de son extension
     * @return le type de fichier
     * @throws ExitException si le fichier n'a pas d'extension ou si l'extension n'est pas celle d'un fichier Brainfuck
     */
    private FileType getType(String pArgument, int separator) throws ExitException
    {
        String extension = pArgument.substring(separator);
        Optional<FileType> typeOptional = Arrays.stream(FileType.values()).filter(fileType -> fileType.getExtension().equals(extension)).findFirst();
        if (typeOptional.isPresent())
            return typeOptional.get();
        else
            throw new ExitException(127, this.getClass().getSimpleName(), "#getType", "Not a brainfuck file");
    }

    /**
     * Creer un objet ReadFile permettant de lire le fichier source.
     *
     * @param pArgument le nom du fichier source
     * @param type      le type du fichier source
     * @return un objet ReadFile associer au fichier source
     * @throws ExitException si le fichier n'existe pas
     */
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

    /**
     * Recupere le flux d'entree.
     *
     * @param interpreter l'interpreter contenant le nom du flux d'entree
     * @return le flux d'entree
     * @throws ExitException si le flux d'entree n'existe pas
     */
    private InputStream getIn(Interpreter interpreter) throws ExitException
    {
        if (interpreter.hasOption(Flag.INPUT))
            try
            {
                return new FileInputStream(interpreter.getOptionValue(Flag.INPUT));
            }
            catch (FileNotFoundException exception)
            {
                throw new ExitException(3, this.getClass().getSimpleName(), "#getIn", exception);
            }
        else
            return System.in;
    }

    /**
     * Recupere le flux de sortie.
     *
     * @param interpreter l'interpreter
     * @return le flux de sortie
     * @throws ExitException si le flux de sortie n'existe pas
     */
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
