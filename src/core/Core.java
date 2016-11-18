package core;

import Language.Language;
import exception.*;

import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public class Core {

    public static int START = 0;
    public static int CAPACITY = 30000;
    public static int MAX = Byte.MAX_VALUE + Byte.MIN_VALUE;
    public static int MIN = 0;

    private byte[] memory;
    private int pointer;
    private int instruction;

    private Instructions[] instructions;
    private Instructions[] program;
    private Language language;


    public Core(Language language) {
        this.language = language;
        this.memory = new byte[CAPACITY];
        instructions = setInstructions();
    }

    public void run() throws ExitException {
        long start = System.currentTimeMillis();

        Pattern[] patterns = new Pattern[instructions.length];
        for (int i = 0; i < instructions.length; i++) {
            patterns[i] = instructions[i].getPattern();
        }
        program = language.compile(instructions, patterns);
        language.call(this);
        language.standardOutput(getMemorySnapshot());
        language.standardOutput(System.currentTimeMillis() - start + " ms");
        language.close();
    }

    public void print() throws LanguageException, CoreException {
        for (instruction=0; instruction < program.length; instruction++)
            program[instruction].execute();
    }

    public void rewrite() {
        for (instruction=0; instruction < program.length; instruction++)
            language.standardOutput(program[instruction].getShortSyntax());
    }

    public void translate() {
        for (instruction=0; instruction < program.length; instruction++)
            program[instruction].getShortSyntax();
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
        return memory[pointer] < 0 ? Byte.MAX_VALUE - Byte.MIN_VALUE + memory[pointer] : memory[pointer];
    }

    public void setValue(int value) {
        memory[pointer] = (byte) (value > Byte.MAX_VALUE ? Byte.MAX_VALUE - Byte.MIN_VALUE - value : value);
    }

    public String getProgramSnapshot() {
        StringBuilder string = new StringBuilder();
        for (Instructions instruction : program) {
            string.append(instruction.getClass().getSimpleName());
            string.append(", ");
        }
        return string.toString();
    }

    public String getMemorySnapshot() {
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

    private Instructions[] setInstructions() {
        return new Instructions[]{new Increment(), new Decrement(), new Left(), new Right(), new Out(), new In(), new Jump(), new Back()};
    }

    private class Increment extends Instructions {
        Increment() {
            super(Pattern.compile("\\+|\\bINCR\\b"), "INCR", '+', new Color(255, 255, 255));
        }

        @Override
        public void execute() throws OverflowException {
            if (memory[pointer] == MAX)
                throw new OverflowException(instruction, pointer);
            memory[pointer]++;
        }
    }

    private class Decrement extends Instructions {
        Decrement() {
            super(Pattern.compile("-|\\bDECR\\b"), "DECR", '-', new Color(75, 0, 130));
        }

        @Override
        public void execute() throws OverflowException {
            if (memory[pointer] == MIN)
                throw new OverflowException(instruction, pointer);
            memory[pointer]--;
        }
    }

    private class Left extends Instructions {
        private Left() {
            super(Pattern.compile("<|\\bLEFT\\b"), "LEFT", '<', new Color(148, 0, 211));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (pointer <= START)
                throw new OutOfMemoryException(instruction, pointer);
            pointer -= 1;
        }
    }

    private class Right extends Instructions {
        private Right() {
            super(Pattern.compile(">|\\bRIGHT\\b"), "RIGHT", '>', new Color(0, 0, 255));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (pointer >= CAPACITY)
                throw new OutOfMemoryException(instruction, pointer);
            pointer += 1;
        }
    }

    private class Out extends Instructions {
        private Out() {
            super(Pattern.compile("\\.|\\bOUT\\b"), "OUT", '.', new Color(0, 255, 0));
        }

        @Override
        public void execute() throws LanguageException {
            language.write(getValue());
        }
    }

    private class In extends Instructions {
        private In() {
            super(Pattern.compile(",|\\bIN\\b"), "In", ',', new Color(255, 255, 0));
        }

        @Override
        public void execute() throws LanguageException {
            setValue(language.read());
        }
    }

    private class Jump extends Instructions {
        private Jump() {
            super(Pattern.compile("\\[|\\bJUMP\\b"), "JUMP", '[', new Color(255, 127, 0));
        }

        @Override
        public void execute() {
            if (memory[pointer] != 0)
                return;
            int close = 1;
            while (close != 0) {
                instruction += 1;
                if (program[instruction] instanceof Back)
                    close -= 1;
                if (program[instruction] instanceof Jump)
                    close += 1;
            }
        }
    }

    private class Back extends Instructions {
        private Back() {
            super(Pattern.compile("\\]|\\bBACK\\b"), "BACK", ']', new Color(255, 0, 0));
        }

        @Override
        public void execute() {
            if (memory[pointer] == 0)
                return;
            int close = -1;
            while (close != 0) {
                instruction -= 1;
                if (program[instruction] instanceof Back)
                    close -= 1;
                if (program[instruction] instanceof Jump)
                    close += 1;
            }
        }
    }
}