package model;

/**
 * Created by user on 21/09/2016.
 */
public class Memory {

    public static int MEMORY_CAPACITY=30000;

    private int[] M;//contain up to 8 bits of unsigned data (denoted as di), i.e., 8i 2 [0; 29999]; di 2 [0; 28 􀀀 1].
    private int p;//pointer to the memory cell currently used by the program
    private int i;//pointer to the next instruction to be executed in the program

    public Memory() {
        M = new int[MEMORY_CAPACITY];
        p = 0;
        i = 0;
    }

    public void incr() {
        M[p]++;
    }

    /**
     * move the memory pointer to the right
     * @exitCode 2 : moving the pointer to the extreme right
     */
    public void right() {
        if (i < M.length) {
            p++;
        }else {
            System.exit(2);
        }
    }

    /**
     * move the memory pointer to the left
     * @exitCode 2 : moving the pointer left to the first cell
     */
    public void left() {
        if (i > 0) {
            p--;
        }else {
            System.exit(2);
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int j = 0; j < M.length; j++) {
            if (s != "") {
                s += "\n";
            }
            if (M[j] != 0) {
                s += "C" + j + ": " + M[j];
            }
        }
        return s;
    }
}
