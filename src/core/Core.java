package core;

import Language.*;
import exception.*;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class Core {

    private final static int START = 0;
    public final static int CAPACITY = 30000;
    private final static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    private final static int MIN = 0;

    private byte[] memory;
    private int pointer;
    private int instruction;

    private Instructions[] instructions;
    private Instructions[] program;
    private Language language;

    private long start;
    private long exec_move;
    private long data_move;
    private long data_write;
    private long data_read;


    public Core(Language language) {
        this.language = language;
        this.memory = new byte[CAPACITY];
        instructions = getInstructions();
    }

    public void run() throws ExitException {
        start = System.currentTimeMillis();
        exec_move = 0;
        data_move = 0;
        data_write = 0;
        data_read = 0;

        Pattern[] patterns = new Pattern[instructions.length];
        for (int i = 0; i < instructions.length; i++) {
            patterns[i] = instructions[i].getPattern();
        }
        program = language.compile(instructions, patterns);
        language.call(this);
        language.close();
    }

    public void print() throws LanguageException, CoreException {
        for (instruction=0; instruction < program.length; instruction++) {
            program[instruction].execute();
            exec_move += 1;
        }

        language.standardOutput(getMemorySnapshot());
        language.standardOutput(getMetrics());
    }

    public void rewrite() {
        for (instruction=0; instruction < program.length; instruction++)
            language.standardOutput(program[instruction].getShortSyntax());
        language.standardOutput("\n");
    }

    public void translate() {
        int size = BitmapImage.SIZE * (int) Math.ceil(Math.sqrt(program.length));
        int[] colorArray = new int[size * size];
        int div, mod;
        for (instruction=0; instruction < program.length; instruction++) {
            div = (instruction * BitmapImage.SIZE) / size * BitmapImage.SIZE;
            for (int line = div * size; line < (div + BitmapImage.SIZE) * size; line += size) {
                mod = (instruction * BitmapImage.SIZE) % size;
                for (int column = mod; column < mod + BitmapImage.SIZE; column++)
                    colorArray[line + column] = program[instruction].getColorSyntax();
            }
        }
        language.imageOutput(colorArray, size);
    }

    public void check() throws NotWellFormedException {
        int check = 0;
        for (instruction=0; instruction<program.length; instruction++) {
            if (check < 0)
                throw new NotWellFormedException();
            if (program[instruction] instanceof Jump)
                check += 1;
            if (program[instruction] instanceof Back)
                check -= 1;
        }
        if (check != 0)
            throw new NotWellFormedException();
    }

    public int getValue() {
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] - MAX: memory[pointer];
    }

    public void setValue(int value) {
        memory[pointer] = (byte) (value > Byte.MAX_VALUE ? Byte.MIN_VALUE - Byte.MAX_VALUE + MAX + value : value);
    }

    public int getPointer() {
        return pointer;
    }

    private String getMetrics() {
        String metrics = "PROG_SIZE: " + program.length + '\n';
        metrics += "EXEC_TIME: " + (System.currentTimeMillis() - start) + " ms\n";
        metrics += "EXEC_MOVE: " + exec_move + '\n';
        metrics += "DATA_MOVE: " + data_move + '\n';
        metrics += "DATA_WRITE: " + data_write + '\n';
        metrics += "DATA_READ: " + data_read + '\n';
        return metrics;
    }

    private String getMemorySnapshot() {
        StringBuilder stringbuilder = new StringBuilder("\n");
        for (pointer=0; pointer<CAPACITY; pointer++) {
            if (memory[pointer] != 0) {
                stringbuilder.append("C");
                stringbuilder.append(pointer);
                stringbuilder.append(": ");
                stringbuilder.append(getValue());
                stringbuilder.append("\n");
            }
        }
        return stringbuilder.toString();
    }

    public Instructions[] getInstructions() {
        return new Instructions[]{new Increment(), new Decrement(), new Left(), new Right(), new Out(), new In(), new Jump(), new Back()};
    }

    private class Increment extends Instructions {
        Increment() {
            super("INCR", '+', new Color(255, 255, 255));
        }

        @Override
        public void execute() throws OverflowException {
            if (memory[pointer] == MAX)
                throw new OverflowException(instruction, pointer);
            memory[pointer]++;
            data_write += 1;
        }
    }

    private class Decrement extends Instructions {
        Decrement() {
            super("DECR", '-', new Color(75, 0, 130));
        }

        @Override
        public void execute() throws OverflowException {
            if (memory[pointer] == MIN)
                throw new OverflowException(instruction, pointer);
            memory[pointer]--;
            data_write += 1;
        }
    }

    private class Left extends Instructions {
        private Left() {
            super("LEFT", '<', new Color(148, 0, 211));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (pointer <= START)
                throw new OutOfMemoryException(instruction, pointer);
            data_move += 1;
            pointer -= 1;
        }
    }

    private class Right extends Instructions {
        private Right() {
            super("RIGHT", '>', new Color(0, 0, 255));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (pointer >= CAPACITY - 1)
                throw new OutOfMemoryException(instruction, pointer);
            data_move += 1;
            pointer += 1;
        }
    }

    private class Out extends Instructions {
        private Out() {
            super("OUT", '.', new Color(0, 255, 0));
        }

        @Override
        public void execute() throws LanguageException {
            language.write(getValue());
            data_read += 1;
        }
    }

    private class In extends Instructions {
        private In() {
            super("IN", ',', new Color(255, 255, 0));
        }

        @Override
        public void execute() throws LanguageException {
            setValue(language.read());
            data_write += 1;
        }
    }

    private class Jump extends Instructions {
        private Jump() {
            super("JUMP", '[', new Color(255, 127, 0));
        }

        @Override
        public void execute() throws NotWellFormedException {
            if (memory[pointer] != 0)
                return;
            int close = 1;
            int brace = instruction;
            while (close != 0) {
                instruction += 1;
                if (instruction >= program.length)
                    throw new NotWellFormedException(brace);
                if (program[instruction] instanceof Back)
                    close -= 1;
                if (program[instruction] instanceof Jump)
                    close += 1;
            }
            data_read += 1;
        }
    }

    private class Back extends Instructions {
        private Back() {
            super("BACK", ']', new Color(255, 0, 0));
        }

        @Override
        public void execute() throws NotWellFormedException {
            if (memory[pointer] == 0)
                return;
            int close = -1;
            int brace = instruction;
            while (close != 0) {
                instruction -= 1;
                if (instruction < 0)
                    throw new NotWellFormedException(brace);
                if (program[instruction] instanceof Back)
                    close -= 1;
                if (program[instruction] instanceof Jump)
                    close += 1;
            }
            data_read += 1;
        }
    }
}