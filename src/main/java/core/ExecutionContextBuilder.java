package core;

import exception.ExitException;
import exception.NotWellFormedException;
import instructions.Executable;
import instructions.Loop;
import language.ReadFile;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Construit le contexte d'exécution
 *
 * @author Alexandre Clement
 * @see ExecutionContext
 * @since 23/12/2016.
 */
public class ExecutionContextBuilder
{
    /**
     * La table utilisée par les boucles
     */
    private Map<Integer, Integer> jumpTable;
    /**
     * Le programme contenant le tableau d'exécutable
     */
    private Deque<Executable> program;
    /**
     * Le tableau des instructions disponibles
     */
    private Executable[] executables;

    /**
     * Permet de construire la jump table
     * une liste de stack (une stack par type de boucle)
     * permet de joindre chaque instruction ouvrant une boucle à l'instruction qui la ferme
     */
    private final List<Deque<Integer>> loops;
    /**
     * le paterne contenant toutes les instructions ainsi que les caractères de commentaire
     */
    private Pattern pattern;
    /**
     * le matcher résultant de l'application du paterne sur une ligne du fichier
     */
    private Matcher matcher;
    /**
     * Le flux d'entrée
     */
    private InputStreamReader in;
    /**
     * Le flux de sortie
     */
    private PrintStream out;
    /**
     * la longueur du programme
     */
    private int length;

    /**
     * Initialise le builder
     */
    public ExecutionContextBuilder()
    {
        jumpTable = new HashMap<>();
        program = new ArrayDeque<>();
        executables = Core.getExecutables();
        loops = new ArrayList<>();
        length = 0;
    }

    /**
     * Ajoute un flux d'entrée
     *
     * @param in le flux d'entrée
     * @return this
     */
    public ExecutionContextBuilder setIn(InputStreamReader in)
    {
        this.in = in;
        return this;
    }

    /**
     * Ajoute un flux de sortie
     *
     * @param out le flux de sortie
     * @return this
     */
    public ExecutionContextBuilder setOut(PrintStream out)
    {
        this.out = out;
        return this;
    }

    /**
     * Ajoute le tableau d'exécutable disponible
     *
     * @param executables le tableau d'exécutable
     * @return this
     */
    public ExecutionContextBuilder setExecutables(Executable[] executables)
    {
        this.executables = executables;
        return this;
    }

    /**
     * Construit le contexte d'exécution
     *
     * @return le contexte d'exécution
     */
    public ExecutionContext build()
    {
        return new ExecutionContext(program.toArray(new Executable[length]), jumpTable, in, out);
    }

    /**
     * Construit un contexte d'exécution à partir d'un fichier
     *
     * @param file le fichier source
     * @return le contexte d'exécution
     * @throws ExitException si le fichier n'exite pas
     */
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

    /**
     * @return true si le caractère est un commentaire, false sinon
     */
    private boolean matchComment()
    {
        return matcher.group(1) != null;
    }

    /**
     * Compile tous les paternes ensemble et rajouter les commentaires i.e #
     *
     * @return le pattern somme de tous les paternes d'instructions
     */
    private Pattern compile(Executable[] executables)
    {
        StringBuilder stringBuilder = new StringBuilder("([" + Instructions.COMMENT + "])");
        for (Executable executable : executables)
        {
            stringBuilder.append("|");
            stringBuilder.append(executable.getInstructions().getPattern());
        }
        return Pattern.compile(stringBuilder.toString());
    }

    /**
     * Ajoute l'instrucion au programme
     *
     * @param executable l'instruction a ajouté au programme
     */
    private void addExecutable(Executable executable)
    {
        program.add(executable);
        Instructions instructions = executable.getInstructions();
        // si c'est une instruction de type boucle
        if (instructions.getLoopType() != null)
            addLoop(executable);
        length += 1;
    }

    /**
     * Ajoute la position de l'exécutable à la jump table
     *
     * @param executable l'exécutable à ajoutée à la jump table
     */
    private void addLoop(Executable executable)
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

    /**
     * Complète la jump table s'il reste des boucles ouvertes non fermées / des boucles fermées non ouverte
     */
    private void completeTable()
    {
        for (Deque<Integer> loop : loops)
        {
            for (Integer brace : loop)
            {
                jumpTable.put(brace, brace);
            }
        }
    }
}
