package core;

import exception.ExitException;
import instructions.Executable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

/**
 * Le contexte d'exécution contenant le programme et le tuple mémoire, pointeur mémoire, pointeur d'instruction
 *
 * @author Alexandre Clement
 * @since 07/12/2016.
 */
public class ExecutionContext
{
    /**
     * La valeur max d'une cellule mémoire = -1
     */
    public static final int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    /**
     * La valeur minimum d'une cellule mémoire = 0
     */
    public static final int MIN = 0;
    /**
     * La capacité par défaut de la mémoire = 30000
     */
    static final int CAPACITY = 30000;
    /**
     * Le programme contenant le tableau d'exécutable
     */
    private final Executable[] program;
    /**
     * La table utilisée par les boucles
     */
    private final Map<Integer, Integer> jumpTable;
    /**
     * La mémoire
     */
    private final byte[] memory;
    /**
     * Le flux d'entrée
     */
    private final InputStreamReader in;
    /**
     * Le flux de sortie
     */
    private final PrintStream out;
    /**
     * Le pointeur d'instruction
     */
    private int instruction;
    /**
     * Le pointeur mémoire
     */
    private int pointer;


    /**
     * Initialise le tuple d'exécution (pointeur d'instruction, pointeur mémoire, mémoire)
     *
     * @param instruction la valeur par défaut du pointeur d'instruction
     * @param pointer     la valeur par défaut du pointeur
     * @param memory      initialisation de la mémoire
     */
    public ExecutionContext(int instruction, int pointer, byte[] memory)
    {
        this(instruction, pointer, memory, null, null, null, null);
    }

    /**
     * Initialise le contexte d'exécution avec les valeurs par défaut pour le pointeur d'instruction, le pointeur mémoire et la mémoire
     *
     * @param program   le programme a exécuté
     * @param jumpTable la table de liens entre les instructions OPTIMISED_JUMP et OPTIMISED_BACK
     * @param in        le flux de données entrant
     * @param out       le flux de données sortant
     */
    public ExecutionContext(Executable[] program, Map<Integer, Integer> jumpTable, InputStreamReader in, PrintStream out)
    {
        this(0, 0, new byte[CAPACITY], program, jumpTable, in, out);
    }

    /**
     * Initialise le contexte d'exécution
     *
     * @param instruction la valeur par défaut de pointeur d'instruction
     * @param pointer     la valeur par défaut du pointeur
     * @param memory      initialisation de la mémoire
     * @param program     le programme a exécuté
     * @param jumpTable   la table de liens entre les instructions OPTIMISED_JUMP et OPTIMISED_BACK
     * @param in          le flux de données entrant
     * @param out         le flux de données sortant
     */
    public ExecutionContext(int instruction, int pointer, byte[] memory, Executable[] program, Map<Integer, Integer> jumpTable, InputStreamReader in, PrintStream out)
    {
        this.instruction = instruction;
        this.pointer = pointer;
        this.memory = memory;
        this.program = program;
        this.jumpTable = jumpTable;
        this.in = in;
        this.out = out;
    }

    /**
     * @return la valeur de la cellule mémoire pointé
     */
    public int printValue()
    {
        return printValue(pointer);
    }

    /**
     * @param pointer le pointeur vers une des cellules mémoire
     * @return la valeur ASCII de la cellule i.e entre 0 et 255
     */
    public int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX : memory[pointer];
    }

    /**
     * @return l'image mémoire
     */
    public String getMemorySnapshot()
    {
        StringBuilder stringbuilder = new StringBuilder();
        for (int cell = 0; cell < CAPACITY; cell++)
        {
            if (memory[cell] != 0)
            {
                stringbuilder.append(String.format("C%d:%4d   ", cell, printValue(cell)));
            }
        }
        return stringbuilder.toString();
    }

    /**
     * Exécute l'intruction pointée
     *
     * @throws ExitException si l'exécution engendre une erreur
     */
    public void execute() throws ExitException
    {
        program[instruction].execute(this);
    }

    /**
     * @return le pointeur d'instruction
     */
    public int getInstruction()
    {
        return instruction;
    }

    /**
     * Incrémente de 1 le pointeur d'instruction i.e pointe l'instruction suivante
     */
    public void nextInstruction()
    {
        instruction += 1;
    }

    /**
     * Décrémente de 1 le pointeur d'instruction i.e pointe l'instruction précédente
     */
    public void previousInstruction()
    {
        instruction -= 1;
    }

    /**
     * @return le pointeur mémoire
     */
    public int getPointer()
    {
        return pointer;
    }

    /**
     * @return true si on peut incrémenter le pointeur mémoire, false sinon
     */
    public boolean hasNextCell()
    {
        return pointer < memory.length - 1;
    }

    /**
     * Incrémente de 1 le pointeur mémoire i.e pointe la cellule suivante
     */
    public void nextCell()
    {
        pointer += 1;
    }

    /**
     * @return true si on peut décrémenter le pointeur mémoire, false sinon
     */
    public boolean hasPreviousCell()
    {
        return pointer > 0;
    }

    /**
     * Décrémente de 1 le pointeur mémoire i.e pointe la cellule précédente
     */
    public void previousCell()
    {
        pointer -= 1;
    }

    /**
     * Incrémente la cellule mémoire pointée de 1
     */
    public void increment()
    {
        memory[pointer] += 1;
    }

    /**
     * Décrémente la cellule mémoire pointée de 1
     */
    public void decrement()
    {
        memory[pointer] -= 1;
    }

    /**
     * @param value la nouvelle valeur de la cellule mémoire pointée (entre Byte.Min_Value et Byte.Max_Value)
     */
    public void in(byte value)
    {
        memory[pointer] = value;
    }

    /**
     * @return la valeur ascii présente dans le flux d'entrée
     * @throws IOException si le flux n'existe pas
     */
    public byte readNextValue() throws IOException
    {
        return (byte) in.read();
    }

    /**
     * @param value affiche la valeur de la cellule mémoire pointée en caractère ascii
     */
    public void out(char value)
    {
        out.print(value);
        out.flush();
    }

    /**
     * @return la valeur de la cellule pointée
     */
    public byte getValue()
    {
        return memory[pointer];
    }

    /**
     * @return l'instruction pointée
     */
    public Executable getCurrentExecutable()
    {
        return program[instruction];
    }

    /**
     * @return les propriétés de l'instruction pointée
     */
    public Instructions getCurrentInstruction()
    {
        return program[instruction].getInstructions();
    }

    /**
     * @return le nombre d'instruction dans le programme
     */
    int getProgramLength()
    {
        return program.length;
    }

    /**
     * @return true si il reste des instructions à exécutée, false sinon
     */
    public boolean hasNextInstruction()
    {
        return instruction < program.length;
    }

    /**
     * @return true si des instructions ont déjà été exécutée, false sinon
     */
    public boolean hasPreviousInstruction()
    {
        return instruction >= 0;
    }

    /**
     * Déplace le pointeur d'instruction jusqu'à la position ciblée
     *
     * @param instruction la position ciblée
     */
    public void bound(int instruction)
    {
        this.instruction = instruction;
    }

    /**
     * Récupère la position de l'instruction lié à celle actuellement pointée
     *
     * @return la position de l'instruction lié
     */
    public int getJumpLink()
    {
        return jumpTable.get(instruction);
    }

    /**
     * Ferme les flux d'entrée et de sortie
     */
    void close() throws ExitException
    {
        try
        {
            in.close();
        }
        catch (IOException e)
        {
            throw new ExitException(3, this.getClass().getSimpleName(), "#close", e);
        }
        out.close();
    }
}
