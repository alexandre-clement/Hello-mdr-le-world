package model;


import interpreter.Display;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
class Memory {
    private final static int MIN = Byte.MIN_VALUE; // -128
    private final static int MAX = Byte.MAX_VALUE; // 127

    /**
     * the total memory capacity
     */
    private final static int MEMORY_CAPACITY=30000;

    /**
     * contain up to 8 bits of unsigned date (denoted as di)
     */
    private byte[] M;
    /**
     * the pointer to the memory cell currently used by used by the program
     */
    private int p;

    Memory() {
        M = new byte[MEMORY_CAPACITY];
        for (int j=0; j<MEMORY_CAPACITY; j++) M[j] = (byte) MIN;
        p = 0;
    }

    private int getValue() { return M[p] - MIN; }

    private int getValue(int p) {
        return M[p] - MIN;
    }

    /**
     * Increment the cell
     */
    void incr() {
        if (M[p] < MAX) M[p]++;
        else Display.exitCode(1);
    }

    /**
     * Decrement the cell
     */
    void decr() {
        if (M[p] > MIN) M[p]--;
        else Display.exitCode(1);
    }

    /**
     * move the memory pointer to the right
     * exitCode 2 : moving the pointer to the extreme right
     */
    void right() {
        if (p < M.length - 1) {
            p++;
        }else {
            Display.exitCode(2);
        }
    }

    /**
     * move the memory pointer to the left
     * exitCode 2 : moving the pointer left to the first cell
     */
    void left() {
        if (p > 0) {
            p--;
        }else {
            Display.exitCode(2);
        }
    }

    void in(char character) {
        M[p] = (byte) ((int) character + MIN);
    }

    char out() {
        return (char) M[p];
    }

    /**
     * @return true if the value of the pointed cell is equal to 0
     */
    boolean dp() { return getValue() == 0; }

    /**
     * @return a readable version of the memory such as : Ci: the value of the cell i
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < M.length; j++) {
            if (getValue(j)!= 0) {
                stringBuilder.append('C');
                stringBuilder.append(j);
                stringBuilder.append(": ");
                stringBuilder.append(getValue(j));
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
