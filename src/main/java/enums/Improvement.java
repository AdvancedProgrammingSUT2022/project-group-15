package enums;

public enum Improvement {
    CAMP("camp", 0, 0,0),
    FARM("farm", 1, 0,0),
    LUMBER_MILL("lumber mill", 0, 0,1),
    MINE("mine", 1, 0,1),
    PASTURE("pasture", 0, 0,0),
    PLANTATION("plantation", 0, 0,0),
    QUARRY("quarry", 0, 0,0),
    TRADING_POST("trading post", 0, 1,0),
    FACTORY("factory", 0, 0,2),
    ROAD("road", 0, 0,0),
    RAILROAD("railroad", 0, 0,0);

    public final String name;
    public final int food;
    public final int gold;
    public final int production;

    Improvement(String name, int food, int gold, int production) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
    }

    public static Improvement getImprovementByName(String improvementName) {
        for (Improvement improvement : Improvement.values()) {
            if(improvement.name.equals(improvementName)){
                return improvement;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
