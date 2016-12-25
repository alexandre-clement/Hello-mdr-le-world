package core;

import exception.ExitException;
import instructions.Executable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

/**
 * Le contexte d'execution contenant le programme et le tuple memoire, pointeur memoire, pointeur d'instruction.
 *
 * @author Alexandre Clement
 * @since 07/12/2016.
 */
public class ExecutionContext
{
    /**
     * La valeur max d'une cellule memoire = {@value}.
     */
    public static final int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    /**
     * La valeur minimum d'une cellule memoire = {@value}.
     */
    public static final int MIN = 0;
    /**
     * La capacite par defaut de la memoire = {@value}.
     */
    static final int CAPACITY = 30000;
    /**
     * Le programme contenant le tableau d'executable.
     */
    private final Executable[] program;
    /**
     * La table utilisee par les boucles.
     */
    private final Map<Integer, Integer> jumpTable;
    /**
     * La memoire.
     */
    private final byte[] memory;
    /**
     * Le flux d'entree.
     */
    private final InputStreamReader in;
    /**
     * Le flux de sortie.
     */
    private final PrintStream out;
    /**
     * Le pointeur d'instruction.
     */
    private int instruction;
    /**
     * Le pointeur memoire.
     */
    private int pointer;


    /**
     * Initialise le tuple d'execution (pointeur d'instruction, pointeur memoire, memoire).
     *
     * @param instruction la valeur par defaut du pointeur d'instruction
     * @param pointer     la valeur par defaut du pointeur
     * @param memory      initialisation de la memoire
     */
    public ExecutionContext(int instruction, int pointer, byte[] memory)
    {
        this(instruction, pointer, memory, null, null, null, null);
    }

    /**
     * Initialise le contexte d'execution avec les valeurs par defaut pour le pointeur d'instruction,
     * le pointeur memoire et la memoire.
     *
     * @param program   le programme a execute
     * @param jumpTable la table de liens entre les instructions OPTIMISED_JUMP et OPTIMISED_BACK
     * @param in        le flux de donnees entrant
     * @param out       le flux de donnees sortant
     */
    public ExecutionContext(Executable[] program, Map<Integer, Integer> jumpTable, InputStreamReader in, PrintStream out)
    {
        this(0, 0, new byte[CAPACITY], program, jumpTable, in, out);
    }

    /**
     * Initialise le contexte d'execution.
     *
     * @param instruction la valeur par defaut de pointeur d'instruction
     * @param pointer     la valeur par defaut du pointeur
     * @param memory      initialisation de la memoire
     * @param program     le programme a execute
     * @param jumpTable   la table de liens entre les instructions OPTIMISED_JUMP et OPTIMISED_BACK
     * @param in          le flux de donnees entrant
     * @param out         le flux de donnees sortant
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
     * @return la valeur de la cellule memoire pointe
     */
    public int printValue()
    {
        return printValue(pointer);
    }

    /**
     * @param pointer le pointeur vers une des cellules memoire
     * @return la valeur ASCII de la cellule i.e entre 0 et 255
     */
    public int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX : memory[pointer];
    }

    /**
     * @return l'image memoire
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
     * Execute l'intruction pointee.
     *
     * @throws ExitException si l'execution engendre une erreur
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
     * Incremente de 1 le pointeur d'instruction i.e pointe l'instruction suivante.
     */
    public void nextInstruction()
    {
        instruction += 1;
    }

    /**
     * Decremente de 1 le pointeur d'instruction i.e pointe l'instruction precedente.
     */
    public void previousInstruction()
    {
        instruction -= 1;
    }

    /**
     * @return le pointeur memoire
     */
    public int getPointer()
    {
        return pointer;
    }

    /**
     * @return true si on peut incrementer le pointeur memoire, false sinon
     */
    public boolean hasNextCell()
    {
        return pointer < memory.length - 1;
    }

    /**
     * Incremente de 1 le pointeur memoire i.e pointe la cellule suivante.
     */
    public void nextCell()
    {
        pointer += 1;
    }

    /**
     * @return true si on peut decrementer le pointeur memoire, false sinon
     */
    public boolean hasPreviousCell()
    {
        return pointer > 0;
    }

    /**
     * Decremente de 1 le pointeur memoire i.e pointe la cellule precedente.
     */
    public void previousCell()
    {
        pointer -= 1;
    }

    /**
     * Incremente la cellule memoire pointee de 1.
     */
    public void increment()
    {
        memory[pointer] += 1;
    }

    /**
     * Decremente la cellule memoire pointee de 1.
     */
    public void decrement()
    {
        memory[pointer] -= 1;
    }

    /**
     * @param value la nouvelle valeur de la cellule memoire pointee (entre Byte.Min_Value et Byte.Max_Value)
     */
    public void in(byte value)
    {
        memory[pointer] = value;
    }

    /**
     * @return la valeur ascii presente dans le flux d'entree
     * @throws IOException si le flux n'existe pas
     */
    public byte readNextValue() throws IOException
    {
        return (byte) in.read();
    }

    /**
     * @param value affiche la valeur de la cellule memoire pointee en caractere ascii
     */
    public void out(char value)
    {
        out.print(value);
        out.flush();
    }

    /**
     * @return la valeur de la cellule pointee
     */
    public byte getValue()
    {
        return memory[pointer];
    }

    /**
     * @return l'instruction pointee
     */
    public Executable getCurrentExecutable()
    {
        return program[instruction];
    }

    /**
     * @return les proprietes de l'instruction pointee
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
     * @return true si il reste des instructions a executee, false sinon
     */
    public boolean hasNextInstruction()
    {
        return instruction < program.length;
    }

    /**
     * @return true si des instructions ont deja ete executee, false sinon
     */
    public boolean hasPreviousInstruction()
    {
        return instruction >= 0;
    }

    /**
     * Deplace le pointeur d'instruction jusqu'a la position ciblee.
     *
     * @param instruction la position ciblee
     */
    public void bound(int instruction)
    {
        this.instruction = instruction;
    }

    /**
     * Recupere la position de l'instruction lie a celle actuellement pointee.
     *
     * @return la position de l'instruction lie
     */
    public int getJumpLink()
    {
        return jumpTable.get(instruction);
    }

    /**
     * Ferme les flux d'entree et de sortie.
     *
     * @throws ExitException si le flux d'entree ne peut pas etre fermer
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
