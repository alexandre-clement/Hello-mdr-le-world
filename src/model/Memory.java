package model;


/**
 * @author SmartCoding
 */
public class Memory {

    private final static int MEMORY_CAPACITY=30000;

    private Cell[] M; //contain up to 8 bits of unsigned data (denoted as di), i.e., 8i 2 [0; 29999]; di 2 [0; 28 􀀀 1].
    private int p; //pointer to the memory cell currently used by the program

    public Memory() {
        M = new Cell[MEMORY_CAPACITY];
        for (int j=0; j<MEMORY_CAPACITY; j++) M[j] = new Cell();

        p = 0;
    }

    public Cell[] getM() {
        return M;
    }

    /**
     * move the memory pointer to the right
     * exitCode 2 : moving the pointer to the extreme right
     */
    public void right() {
        if (p < M.length) {
            p++;
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
        }else {
            System.exit(2);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < M.length; j++) {
            if (M[j].getValue()!= 0) {
                stringBuilder.append('C');
                stringBuilder.append(j);
                stringBuilder.append(": ");
                stringBuilder.append(M[j].toString());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
