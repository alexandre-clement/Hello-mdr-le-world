package model;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 *
 */
public class OperatingSystem {
    private Memory memory;
    private int i;

    public OperatingSystem() {
        memory = new Memory();
        i = 0;
    }

    public String getMemoryContent() {
        return memory.toString();
    }

    public int getI() {
        return i;
    }

    public void nextI() {
        i++;
    }

    public void incr() {
        memory.incr();
    }

    public void decr() {
        memory.decr();
    }

    public void left() {
        memory.left();
    }

    public void right() {
        memory.right();
    }

    public void in() { memory.in(); }

    public void out() { memory.out(); }

    public boolean dp() { return memory.dp(); }

    public void bound(int i) { this.i = i; }
}
