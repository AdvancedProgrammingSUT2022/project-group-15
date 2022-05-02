package enums;

public enum NeighborHex {
    UP(-2, 0),
    DOWN(2, 0),
    DOWNLEFT(+1, -1),
    DOWNRIGHT(+1, +1),
    UPLEFT(-1, -1),
    UPRIGHT(-1, +1);


    public final int xDiff;
    public final int yDiff;

    NeighborHex(int xDiff, int yDiff) {
        this.xDiff = xDiff;
        this.yDiff = yDiff;
    }
}
