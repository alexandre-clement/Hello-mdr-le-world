package model;

import interpreter.Display;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
class Cell {
    private final static int MIN = Byte.MIN_VALUE; // -128
    private final static int MAX = Byte.MAX_VALUE; // 127
    /**
     * the value of the cell such as MIN < value < MAX
     */
    private byte value;


    Cell() {
        this.value = (byte) MIN;
    }

    /**
     * @return the value of the cell
     */
    int getValue() {
        return value - MIN;
    }

    /**
     * Increment the cell
     * exit code 1 if the cell is at the maximum value
     */
    void incr() {
        if (value < MAX) value++;
        else Display.exitCode(1);
    }

    /**
     * Decrement the cell
     * exit code 1 if the cell is at the minimum value
     */
    void decr() {
        if (value > MIN) value--;
        else Display.exitCode(1);
    }

    void in() { }

    /**
     * @return the value of the cell
     */
    @Override
    public String toString() { return String.valueOf(value - MIN);}
}


