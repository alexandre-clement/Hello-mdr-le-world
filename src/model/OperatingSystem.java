package model;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 *
 */
public class OperatingSystem {
    /**
     * the memory that will be used
     */
    private Memory memory;
    /**
     * the instruction pointer
     */
    private int i;

    public OperatingSystem() {
        memory = new Memory();
        i = 0;
    }

    /**
     * @return the content of the memory
     */
    public String getMemoryContent() {
        return memory.toString();
    }

    /**
     * @return the instruction pointer i
     */
    public int getI() {
        return i;
    }

    /**
     * go to the next instruction
     */
    public void nextI() {
        i++;
    }

    /**
     * increment the memory pointed cell
     */
    public void incr() {
        memory.incr();
    }

    /**
     * decrement the memory pointed cell
     */
    public void decr() {
        memory.decr();
    }

    /**
     * move the memory pointer to the left
     */
    public void left() {
        memory.left();
    }

    /**
     * move the memory pointer to the right
     */
    public void right() {
        memory.right();
    }

    public void in(char character) { memory.in(character); }

    public char out() { return memory.out(); }

    public boolean dp() { return memory.dp(); }

    /**
     * bounce to a new instruction
     * @param i the new instructions pointer
     */
    public void bound(int i) { this.i = i; }
}
