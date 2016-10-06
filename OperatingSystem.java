package ComputationalModel;

import Interpreter.Display;
import Interpreter.ExitCode;

import java.util.Arrays;


public class OperatingSystem {
    Memory memory;

    public OperatingSystem() {
        memory = new Memory();
    }
    public OperatingSystem(int memorySize) {
        memory = new Memory(memorySize);
    }
    public OperatingSystem(Memory M) {
        memory = M;
    }

    public Memory getMemory() {
        return memory.copy();
    }

    public boolean Incr() { return memory.cells[memory.pointer].incr(); }

    public boolean Decr() { return memory.cells[memory.pointer].decr(); }

    public boolean Right() { return memory.right(); }

    public boolean Left() { return memory.left(); }

    public boolean In(int value) { return memory.cells[memory.pointer].in(value);}

    public boolean Out() {
        String output = memory.cells[memory.pointer].out();
        if (output != null) {
            Display.display(output);
            return true;
        }
        return false;
    }


}
