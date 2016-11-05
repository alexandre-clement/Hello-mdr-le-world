package brainfuck;

import exception.OutOfMemoryException;
import exception.OverflowException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
class Core {
    private Memory memory;

    Core() {
        memory = new Memory();
    }

    String memoryContent() {
        return memory.toString();
    }

    List<Instruction> getNewInstruction() {
        List<Instruction> instructions = new ArrayList<>(Arrays.asList(new Incr(), new Decr(), new Right(), new Left(), new Out(), new In(), new Null()));
        instructions.addAll(Arrays.asList(new LinkLoop(new Jump(), new Back()).get()));
        return instructions;
    }

    interface Executable {
        void execute() throws OverflowException, OutOfMemoryException;
    }

    abstract class Instruction implements Executable {
        private String longSyntax;
        private Character shortSyntax;
        private Color colorSyntax;

        String longSyntax() { return longSyntax; }
        Character shortSyntax() { return shortSyntax; }
        Color colorSyntax() { return colorSyntax; }

        @Override
        public String toString() {
            return longSyntax();
        }

        Instruction(String longSyntax, Character shortSyntax, Color colorSyntax) {
            this.longSyntax = longSyntax;
            this.shortSyntax = shortSyntax;
            this.colorSyntax = colorSyntax;
        }
    }

    private class Incr extends Instruction {
        private Incr() {
            super("INCR", '+', new Color(255, 255, 255));
        }

        @Override
        public void execute() throws OverflowException {
            memory.incr();
            memory.nextI();
        }
    }

    private class Decr extends Instruction {
        private Decr() {
            super("DECR", '-', new Color(75, 0, 130));
        }

        @Override
        public void execute() throws OverflowException {
            memory.decr();
            memory.nextI();
        }
    }

    private class Left extends Instruction {
        private Left() {
            super("LEFT", '<', new Color(148, 0, 211));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            memory.left();
            memory.nextI();
        }
    }

    private class Right extends Instruction {
        private Right() {
            super("RIGHT", '>', new Color(0, 0, 255));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            memory.right();
            memory.nextI();
        }
    }

    private class Out extends Instruction {
        private Out() {
            super("OUT", '.', new Color(0, 255, 0));
        }

        @Override
        public void execute() {
            memory.out();
            memory.nextI();
        }
    }

    private class In extends Instruction {
        private In() {
            super("In", ',', new Color(255, 255, 0));
        }

        @Override
        public void execute() {
            memory.in('\0');
            memory.nextI();
        }
    }

    abstract class Loop extends Instruction {
        private Loop associated;
        private Loop(String longSyntax, Character shortSyntax, Color colorSyntax) {
            super(longSyntax, shortSyntax, colorSyntax);
        }

        Loop getAssociated() {
            return associated;
        }

        private void setAssociated(Loop associated) {
            this.associated = associated;
        }
    }

    private class LinkLoop {
        private Instruction jump;
        private Instruction back;

        LinkLoop(Loop jump, Loop back) {
            jump.setAssociated(back);
            this.jump = jump;
            this.back = back;
        }

        Instruction[] get() { return new Instruction[] {jump, back}; }
    }

    private class Jump extends Loop {
        private Jump() {
            super("JUMP", '[', new Color(255, 127, 0));
        }

        @Override
        public void execute() {
            if (memory.dp())
            memory.nextI();
        }
    }

    private class Back extends Loop {
        private Back() {
            super("BACK", ']', new Color(255, 0, 0));
        }

        @Override
        public void execute() {
            if (memory.dp())
            memory.nextI();
        }
    }

    private class Null extends Instruction {
        private Null() {
            super("NULL", '\0', new Color(0, 0, 0));
        }

        @Override
        public void execute() throws OutOfMemoryException {}
    }
}
