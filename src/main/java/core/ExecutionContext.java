package core;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class ExecutionContext
{
    public final static int CAPACITY = 30000;
    public final static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    public final static int MIN = 0;

    public Instructions[] program;
    public Map<Integer, Integer> jumpTable;

    public int instruction;
    public byte[] memory;
    public int pointer;

    //the input stream and output stream
    public InputStreamReader in;
    public PrintStream out;

    /**
     * @param program le programme a exécuté
     * @param jumpTable la table de liens entre les instructions JUMP et BACK
     * @param in le flux de données entrant
     * @param out le flux de données sortant
     */
    public ExecutionContext(Instructions[] program, Map<Integer, Integer> jumpTable, InputStreamReader in, PrintStream out)
    {
        this(0, 0, new byte[CAPACITY], program, jumpTable, in, out);
    }

    /**
     * @param instruction la valeur par défaut de pointeur d'instruction
     * @param pointer la valeur par défaut du pointeur
     * @param memory initialisation de la mémoire
     * @param program le programme a exécuté
     * @param jumpTable la table de liens entre les instructions JUMP et BACK
     * @param in le flux de données entrant
     * @param out le flux de données sortant
     */
    public ExecutionContext(int instruction, int pointer, byte[] memory, Instructions[] program, Map<Integer, Integer> jumpTable, InputStreamReader in, PrintStream out)
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
     *
     * @param pointer le pointeur vers une des cellules mémoire
     * @return la valeur ASCII de la cellule i.e entre 0 et 255
     */
    public int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX: memory[pointer];
    }

    /**
     * @return the memory snapshot
     */
    String getMemorySnapshot()
    {
        StringBuilder stringbuilder = new StringBuilder();
        for (int cell=0; cell<CAPACITY; cell++)
        {
            if (memory[cell] != 0)
            {
                stringbuilder.append(String.format("C%d:%4d   ", cell, printValue(cell)));
            }
        }
        return stringbuilder.toString();
    }
}
