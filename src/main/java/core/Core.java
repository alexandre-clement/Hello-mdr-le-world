package core;

import exception.*;
import interpreter.Flag;
import language.BitmapImage;

import java.io.*;
import java.util.Deque;

/**
 * @author Alexandre Clement
 * @author TANG Yi
 *         Created the 16/11/2016.
 */
public class Core
{

    private final static int CAPACITY = 30000;
    public final static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    public final static int MIN = 0;
    //the table of all instructions of the program in the file
    public Instructions[] program;
    private Probe probe;

    public int instruction;
    public byte[] memory;
    public int pointer;
    //the input stream and output stream
    public InputStreamReader in;
    public PrintStream out;

    private String filename;

    public Core(String filename, InputStreamReader in, PrintStream out)
    {
        this(filename, in, out, 0, new byte[CAPACITY], 0);
    }

    public Core(String filename, InputStreamReader in, PrintStream out, int instruction, byte[] memory, int pointer)
    {
        this.filename = filename;
        this.instruction = instruction;
        this.memory = memory;
        this.pointer = pointer;
        this.in = in;
        this.out = out;
    }

    /**
     * @return the value of the cell that the pointer points
     */
    public int printValue()
    {
        return printValue(pointer);
    }

    /**
     *
     * @param pointer which cell of the memory to return
     * @return the value the pointer points
     */
    private int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX: memory[pointer];
    }

    /**
     * run the options of the user
     * @param flags options the user puts in
     * @param program the instructions
     */
    public void run(Deque<Flag> flags, Instructions... program) throws ExitException
    {
        probe = new Probe();
        for (Flag flag : flags)
        {
            switch (flag)
            {
                case METRICS:
                    probe.addMeter(new Probe.Metrics(program.length));
                    break;
                case TRACE:
                    probe.addMeter(new Probe.Trace(filename));
                    break;
            }
        }
        this.program = program;
        do {
            switch (flags.pop())
            {
                case PRINT:
                    print();
                    break;
                case REWRITE:
                    rewrite();
                    break;
                case TRANSLATE:
                    translate();
                    break;
                case CHECK:
                    check();
                    break;
            }
        } while (!flags.isEmpty());
    }

    /**
     * the '-p' option: execution of the program and print out the memory snapshot
     */
    private void print() throws LanguageException, CoreException
    {
        for (instruction=0; instruction<program.length; instruction++)
        {
            program[instruction].execute(this);
            probe.acknowledge(this);
        }
        probe.getResult();
        standardOutput("\n" + getMemorySnapshot());
    }

    /**
     * the '--rewrite' option: print out the short syntax of the program
     */
    private void rewrite()
    {
        for (instruction=0; instruction<program.length; instruction++)
            standardOutput(program[instruction].getShortcut());
        standardOutput('\n');
    }

    /**
     * the '--translate' option: translate the program to the color syntax and create a image file
     */
    private void translate()
    {

        int size = BitmapImage.SIZE * (int) Math.ceil(Math.sqrt(program.length));
        int[] colorArray = new int[size * size];
        int div, mod;
        for (instruction=0; instruction < program.length; instruction++)
        {
            div = (instruction * BitmapImage.SIZE) / size * BitmapImage.SIZE;
            for (int line = div * size; line < (div + BitmapImage.SIZE) * size; line += size)
            {
                mod = (instruction * BitmapImage.SIZE) % size;
                for (int column = mod; column < mod + BitmapImage.SIZE; column++)
                    colorArray[line + column] = program[instruction].getColor().getRGB();
            }
        }
        try
        {
            BitmapImage.createImage(filename + "_out.bmp", colorArray, size);
        }
        catch (IOException exception)
        {
            System.err.println("Error with translated image");
        }
    }

    /**
     * the '--check' option: check JUMP and BACK in the program are well formed
     * @throws NotWellFormedException if the program is not well formed
     */
    private void check() throws NotWellFormedException
    {
        int close = 0;
        for (Instructions instructions : program)
        {
            if (close < 0)
                throw new NotWellFormedException();
            if (instructions == Instructions.JUMP)
                close += 1;
            else if (instructions == Instructions.BACK)
                close -= 1;
        }
        if (close != 0)
            throw new NotWellFormedException();
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



    /**
     * print out the parameter
     * @param object to be print out
     */
    private static void standardOutput(Object object)
    {
        System.out.print(object);
    }
}