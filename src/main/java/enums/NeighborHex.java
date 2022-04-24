package enums;

public enum NeighborHex {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, +1),
    UPRIGHT(1, 1),
    DOWNRIGHT(-1, 1);

    public final int xDiff;
    public final int yDiff;

    NeighborHex(int xDiff, int yDiff) {
        this.xDiff = xDiff;
        this.yDiff = yDiff;
    }
}
