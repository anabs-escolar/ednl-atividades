
public class Cell {
    private int row;
    private int column;
    private int value;

    public Cell(int r, int c, int v) {
        this.row = r;
        this.column = c;
        this.value = v;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
