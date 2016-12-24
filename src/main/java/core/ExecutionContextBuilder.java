package core;

import exception.ExitException;
import exception.NotWellFormedException;
import instructions.Executable;
import instructions.Loop;
import language.Language;
import language.ReadFile;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 23/12/2016.
 */
public class ExecutionContextBuilder
{
    // la table des boucles
    private Map<Integer, Integer> jumpTable;
    // le programme contenant les instructions contenues dans le fichier
    private Deque<Executable> program;
    // la liste des instructions disponibles
    private Executable[] executables;
    // un liste de stack (une stack par type de boucle)
    // permet de joindre chaque instruction ouvrant une boucle à l'instruction qui la ferme
    private final List<Deque<Integer>> loops;
    // le paterne contenant toutes les instructions ainsi que les caractères de commentaire
    private Pattern pattern;
    // le matcher résultant de l'application du paterne sur une ligne du fichier
    private Matcher matcher;
    private InputStreamReader in;
    private PrintStream out;
    // la longueur du programme
    private int length;


    public ExecutionContextBuilder()
    {
        jumpTable = new HashMap<>();
        program = new ArrayDeque<>();
        executables = Core.getExecutables();
        loops = new ArrayList<>();
        length = 0;
    }

    public ExecutionContextBuilder setIn(InputStreamReader in)
    {
        this.in = in;
        return this;
    }

    public ExecutionContextBuilder setOut(PrintStream out)
    {
        this.out = out;
        return this;
    }

    public ExecutionContextBuilder setExecutables(Executable[] executables)
    {
        this.executables = executables;
        return this;
    }

    public ExecutionContext build()
    {
        return new ExecutionContext(program.toArray(new Executable[length]), jumpTable, in, out);
    }

    public ExecutionContext buildFromFile(ReadFile file) throws ExitException
    {
        for (int i = 0; i < Instructions.LoopType.values().length; i++)
        {
            loops.add(new ArrayDeque<>());
        }
        pattern = compile(executables);
        for (String line = file.next(); line != null; line = file.next())
        {
            matchLine(line);
        }
        completeTable();
        return build();
    }

    private void matchLine(String line) throws NotWellFormedException
    {
        matcher = pattern.matcher(line);
        while (matcher.find())
        {
            if (matchComment())
                break;
            matchExecutable();
        }
    }

    private void matchExecutable() throws NotWellFormedException
    {
        // sinon on regarde quel instructions a été trouvé dans la ligne
        for (int i = 0; i < executables.length; i++)
            if (matcher.group(i + 2) != null)
                // l'instruction i a été trouvé, on l'ajoute a notre liste programme
                addExecutable(executables[i]);
    }

    private boolean matchComment()
    {
        // si c'est un commentaire
        return matcher.group(1) != null;
    }

    /**
     * Compile tous les paternes ensemble et rajouter les commentaires i.e #
     *
     * @return le pattern somme de tous les paternes d'instructions
     */
    private Pattern compile(Executable[] executables)
    {
        StringBuilder stringBuilder = new StringBuilder("([" + Language.COMMENT + "])");
        for (Executable executable : executables)
        {
            stringBuilder.append("|");
            stringBuilder.append(executable.getInstructions().getPattern());
        }
        return Pattern.compile(stringBuilder.toString());
    }

    /**
     * @param executable l'instruction a ajouté au programme
     * @throws NotWellFormedException si l'instruction ferme un boucle non ouverte
     */
    private void addExecutable(Executable executable) throws NotWellFormedException
    {
        program.add(executable);
        Instructions instructions = executable.getInstructions();
        // si c'est une instruction de type boucle
        if (instructions.getLoopType() != null)
            addLoop(executable);
        length += 1;
    }

    private void addLoop(Executable executable) throws NotWellFormedException
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
            jumpTable.put(length, length);
    }

    private void completeTable()
    {
        // on complete la jump table
        for (Deque<Integer> loop : loops)
        {
            for (Integer brace : loop)
            {
                jumpTable.put(brace, brace);
            }
        }
    }
}
