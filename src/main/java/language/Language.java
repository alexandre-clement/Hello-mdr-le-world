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
import macro.Macro;
import macro.MacroBuilder;

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
    private final String filename;
    private final ReadFile file;
    private final InputStreamReader in;
    private final PrintStream out;

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
     *
     * @return le pattern somme de tous les paternes d'instructions
     */
    private Pattern compile(Executable[] executables)
    {
        StringBuilder stringBuilder = new StringBuilder("([" + COMMENT + "])");
        for (Executable executable : executables)
        {
            stringBuilder.append("|");
            stringBuilder.append(executable.getInstructions().getPattern());
        }
        return Pattern.compile(stringBuilder.toString());
    }

    /**
     * Créée l'exécution contexte du programme
     *
     * @return l'execution contexte du programme
     * @throws LanguageException si le fichier n'existe pas
     */
    public ExecutionContext getExecutionContext() throws LanguageException, NotWellFormedException
    {
        // la table des boucles
        HashMap<Integer, Integer> jumpTable = new HashMap<>();
        // le programme contenant les instructions contenues dans le fichier
        Deque<Executable> program = new ArrayDeque<>();
        // la liste des instructions disponibles
        Executable[] executables = Core.getExecutables();
        // un liste de stack (une stack par type de boucle)
        // permet de joindre chaque instruction ouvrant une boucle à l'instruction qui la ferme
        List<Deque<Integer>> loops = new ArrayList<>();
        // on crée un stack par type de boucle
        for (int i = 0; i < Instructions.LoopType.values().length; i++)
        {
            loops.add(new ArrayDeque<>());
        }

        // les macros présentent dans le fichier
        Deque<Macro> macros = new MacroBuilder(file).findMacro();
        // le paterne contenant toutes les instructions ainsi que les caractères de commentaire
        Pattern pattern = compile(executables);
        // le matcher résultant de l'application du paterne sur une ligne du fichier
        Matcher matcher;
        // la longueur du programme
        int length = 0;

        try
        {
            // pour chaque ligne du fichier
            for (String line = file.next(); line != null; line = file.next())
            {
                for (Macro macro : macros)
                {
                    line = macro.match(line);
                }
                // on applique le paterne à la ligne
                matcher = pattern.matcher(line);
                // tant que l'on a une instruction contenue dans la ligne
                while (matcher.find())
                {
                    // si c'est un commentaire
                    if (matcher.group(1) != null)
                        break;

                    // sinon on regarde quel instructions a été trouvé dans la ligne
                    for (int i = 0; i < executables.length; i++)
                        if (matcher.group(i + 2) != null)
                            // l'instruction i a été trouvé, on l'ajoute a notre liste programme
                            addExecutable(program, loops, jumpTable, executables[i], length++);
                }
            }
            // on ferme le fichier
            file.close();
        }
        catch (IOException exception)
        {
            throw new LanguageException(127, "File not found");
        }
        if (!loops.stream().allMatch(Deque::isEmpty))
            throw new NotWellFormedException(length);
        return new ExecutionContext(program.toArray(new Executable[length]), jumpTable, in, out);
    }

    /**
     * @param program    le programme a remplir
     * @param loops      la liste de pile contenant les indices des boucles
     * @param jumpTable  la table des boucles
     * @param executable l'instruction a ajouté au programme
     * @param length     la taille du programme
     * @throws NotWellFormedException si l'instruction ferme un boucle non ouverte
     */
    private void addExecutable(Deque<Executable> program, List<Deque<Integer>> loops, HashMap<Integer, Integer> jumpTable, Executable executable, int length) throws NotWellFormedException
    {
        program.add(executable);
        Instructions instructions = executable.getInstructions();
        // si c'est une instruction de type boucle
        if (instructions.getLoopType() != null)
            addLoop(loops, jumpTable, executable, length);
    }

    /**
     * @param loops      la liste de pile contenant les indices des boucles
     * @param jumpTable  la table des boucles
     * @param executable l'instruction a ajouté au programme
     * @param length     la taille du programme
     * @throws NotWellFormedException si l'instruction ferme un boucle non ouverte
     */
    private void addLoop(List<Deque<Integer>> loops, HashMap<Integer, Integer> jumpTable, Executable executable, int length) throws NotWellFormedException
    {
        Loop loop = (Loop) executable;
        Instructions instructions = executable.getInstructions();
        int ordinal = instructions.getLoopType().ordinal();
        // si c'est une instruction ouvrant une boucle
        if (loop.open())
        {
            // on ajoute la position de l'instruction a la pile
            loops.get(ordinal).addLast(length);
        }
        // sinon si il y a un boucle ouverte
        else if (!loops.get(ordinal).isEmpty())
        {
            // on complete la table des boucles et on retire la boucle de la pile
            jumpTable.put(loops.get(ordinal).peekLast(), length);
            jumpTable.put(length, loops.get(ordinal).pollLast());
        }
        // sinon on a une instruction fermante alors qu'aucune boucle n'est ouverte i.e erreur
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
