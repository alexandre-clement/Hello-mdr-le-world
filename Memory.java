package ComputationalModel;


import java.util.Arrays;
import java.util.stream.Collectors;

public class Memory {
    Cell[] cells;
    int pointer;


    public Memory() {
        this.cells = new Cell[30000];
        Arrays.fill(cells, new Cell());
    }

    public Memory(int memoryCapacity) {
        this.cells = new Cell[memoryCapacity];
        Arrays.fill(cells, new Cell());
    }

    public Memory(Cell[] memory) {
        this.cells = memory;
    }

    boolean left() {
        if (pointer > 0) { pointer--; return true; }
        else return false;
    }

    boolean right() {
        if (pointer < 29999) {
            pointer++;
            return true;
        } else return false;
    }

    public String toString() {
        return Arrays.stream(cells).filter(cell -> cell.getValue() > 0).map(Cell::toString).collect(Collectors.joining(", "));
    }

    Memory copy() {
        return new Memory(cells);
    }
    public Cell[] getCells() {
        return cells.clone();
    }

}
