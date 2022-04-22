package enums;

public enum Direction {
    RIGHT("right"),
    LEFT("left"),
    UP("up"),
    DOWN("down");

    public final String name;

    Direction(String name){
        this.name = name;
    }

    public static Direction getDirectionByName(String name){
        for (Direction direction : Direction.values()) {
            if(direction.name.equals(name)) return direction;
        }
        return null;
    }
}
