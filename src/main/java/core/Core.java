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

    public Instructions[] program;

    public int instruction;
    public byte[] memory;
    public int pointer;

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

    public int printValue()
    {
        return printValue(pointer);
    }

    private int printValue(int pointer)
    {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX: memory[pointer];
    }

    public void run(Deque<Flag> flags, Instructions... program) throws ExitException
    {
        this.program = program;
        do {
            switch (flags.pop()) {
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

    private void print() throws LanguageException, CoreException
    {
        for (; instruction<program.length; instruction++)
            program[instruction].execute(this);
        standardOutput("\n" + getMemorySnapshot());
    }

    private void rewrite()
    {
        for (; instruction<program.length; instruction++)
            standardOutput(program[instruction].getShortcut());
        standardOutput('\n');
    }

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

    private void metrics() throws LanguageException, CoreException
    {
        long exec_move = 0;
        long data_move = 0;
        long data_write = 0;
        long data_read = 0;
        long start = System.currentTimeMillis();

        for (; instruction<program.length; instruction++)
        {
            program[instruction].execute(this);
            switch (program[instruction].getType())
            {
                case DATA_WRITE:
                    data_write += 1;
                    break;
                case DATA_READ:
                    data_read += 1;
                    break;
                case DATA_MOVE:
                    data_move += 1;
                    break;
            }
            exec_move += 1;
        }
        standardOutput(getMetrics(System.currentTimeMillis() - start, exec_move, data_move, data_write, data_read));
    }

    private void trace() throws CoreException, LanguageException
    {
        String log;
        try {
            Writer logFile = new FileWriter(filename + ".log");
            for (int stepNumber = 1; instruction < program.length; stepNumber++, instruction++) {
                program[instruction].execute(this);
                log = String.format("Execution step: %10d | Execution pointer: %10d | Data pointer: %10d | %s%n", stepNumber, instruction, pointer, getMemorySnapshot());
                logFile.write(log);
                logFile.flush();
            }
            logFile.close();
        }
        catch (IOException exception)
        {
            System.err.println("Error with logfile");
        }
    }

    private String getMemorySnapshot()
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

    private String getMetrics(long time, long exec_move, long data_move, long data_write, long data_read)
    {
        String metrics = "\nPROG_SIZE: " + program.length + '\n';
        metrics += "EXEC_TIME: " + time + " ms\n";
        metrics += "EXEC_MOVE: " + exec_move + '\n';
        metrics += "DATA_MOVE: " + data_move + '\n';
        metrics += "DATA_WRITE: " + data_write + '\n';
        metrics += "DATA_READ: " + data_read + '\n';
        return metrics;
    }

    private static void standardOutput(Object object)
    {
        System.out.print(object);
    }
}