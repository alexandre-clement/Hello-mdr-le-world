package system;

import model.Memory;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class OperatingSystem {
    private Memory memory;
    private int i;

    public OperatingSystem() {
        memory = new Memory();
        i = 0;
    }

    public Memory getMemory() {
        return memory;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void incr() {
    }

    public void left() {
        memory.left();
    }

    public void right() {
        memory.right();
    }
}
