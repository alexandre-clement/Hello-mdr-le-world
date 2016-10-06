package model;

/**
 * @author SmartCoding
 */
public class Memory {

    private final static int MEMORY_CAPACITY=30000;

    private int[] M;//contain up to 8 bits of unsigned data (denoted as di), i.e., 8i 2 [0; 29999]; di 2 [0; 28 􀀀 1].
    private int p;//pointer to the memory cell currently used by the program
    private int i;//pointer to the next instruction to be executed in the program

    public Memory() {
        M = new int[MEMORY_CAPACITY];
        p = 0;
        i = 0;
    }

    public int getI() {
        return i;
    }

    public void incr() {
        M[p]++;
        i++;
    }

    /**
     * move the memory pointer to the right
     * exitCode 2 : moving the pointer to the extreme right
     */
    public void right() {
        if (p < M.length) {
            p++;
            i++;
        }else {
            System.exit(2);
        }
    }

    /**
     * move the memory pointer to the left
     * exitCode 2 : moving the pointer left to the first cell
     */
    public void left() {
        if (p > 0) {
            p--;
            i++;
        }else {
            System.exit(2);
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int j = 0; j < M.length; j++) {
            if (M[j] != 0) {
                if (!"".equals(s)) {
                    s += "\n";
                }
                s += "C" + j + ": " + M[j];
            }
        }
        return s;
    }
}