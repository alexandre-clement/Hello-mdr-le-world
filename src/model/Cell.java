package model;


import interpreter.Display;

class Cell {
    final private int MIN = Byte.MIN_VALUE;//-128
    final private int MAX = Byte.MAX_VALUE;//127
    private byte value;


    Cell() {
        this.value = (byte) MIN;
    }

    public int getValue() {
        return value - MIN;
    }

    void incr() {
        if (value < MAX) {
            value++;
        }else {
            //
        }
    }

    boolean decr() {
        if (value > MIN) { value--; return true; }
        else return false;
    }

    public boolean in(int value) {
        if (value >= 0 && value <= MAX - MIN) {
            this.value = (byte) (value + MIN);
            return true;
        }
        return false;
    }

    public String out() { return toString(); }

    public String toString() { return String.valueOf(value - MIN);}
}


