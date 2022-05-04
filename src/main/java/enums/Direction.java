package enums;

public enum Direction {
    RIGHT("right",0,+1),
    LEFT("left",0,-1),
    UP("up",-1,0),
    DOWN("down",1,0);

    public final String name;
    public final int xDiff;
    public final int yDiff;

    Direction(String name, int xDiff, int yDiff) {
        this.name = name;
        this.xDiff = xDiff;
        this.yDiff = yDiff;
    }

    public static Direction getDirectionByName(String name){
        for (Direction direction : Direction.values()) {
            if(direction.name.equals(name)) return direction;
        }
        return null;
    }
}
