package model;

import interpreter.Display;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
class Cell {
    final private int MIN = Byte.MIN_VALUE; // -128
    final private int MAX = Byte.MAX_VALUE; // 127
    private byte value;


    Cell() {
        this.value = (byte) MIN;
    }

    int getValue() {
        return value - MIN;
    }

    void incr() {
        if (value < MAX) value++;
        else Display.exitCode(1);
    }

    void decr() {
        if (value > MIN) value--;
        else Display.exitCode(1);
    }

    @Override
    public String toString() { return String.valueOf(value - MIN);}
}


