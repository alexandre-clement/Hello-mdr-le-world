package core;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class ExecutionContext
{
    final static int CAPACITY = 30000;
    public final static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    public final static int MIN = 0;

    private Instructions[] program;
    private Map<Integer, Integer> jumpTable;

    private int instruction;
    private byte[] memory;
    private int pointer;

    //the input stream and output stream
    private InputStreamReader in;
    private PrintStream out;

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
    private int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX: memory[pointer];
    }

    /**
     * @return the memory snapshot
     */
    public String getMemorySnapshot()
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

    public int getInstruction()
    {
        return instruction;
    }

    public void nextInstruction()
    {
        instruction += 1;
    }

    public void previousInstruction()
    {
        instruction -= 1;
    }

    public int getPointer()
    {
        return pointer;
    }

    public boolean hasNextCell()
    {
        return pointer < memory.length - 1;
    }

    public void nextCell()
    {
        pointer += 1;
    }

    public boolean hasPreviousCell()
    {
        return pointer > 0;
    }

    public void previousCell()
    {
        pointer -= 1;
    }

    public void increment()
    {
        memory[pointer] += 1;
    }

    public void decrement()
    {
        memory[pointer] -= 1;
    }

    public void in(byte value)
    {
        memory[pointer] = value;
    }

    public byte readNextValue() throws IOException
    {
        return (byte) in.read();
    }

    public void out(char value)
    {
        out.print(value);
        out.flush();
    }

    public byte getValue()
    {
        return memory[pointer];
    }

    public Instructions getCurrentInstruction()
    {
        return program[instruction];
    }

    int getProgramLength()
    {
        return program.length;
    }

    public boolean hasNextInstruction()
    {
        return instruction < program.length;
    }

    public boolean hasPreviousInstruction()
    {
        return instruction >= 0;
    }

    public void bound(int instruction)
    {
        this.instruction = instruction;
    }

    public int getJumpLink()
    {
        return jumpTable.get(instruction);
    }

    void close()
    {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("This should not happen: fail at closing in stream");
        }
        out.close();
    }
}
