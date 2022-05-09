package enums;

/**
 * @author Parsa
 */
public enum Resource {
    NULL("null",0,0,0, Improvement.Null, "null"),
    BANANA("banana", 1, 0, 0, Improvement.PLANTATION, "scoring"),
    COW("cow", 1, 0, 0, Improvement.PASTURE, "scoring"),
    GAZELLE("gazelle", 1, 0, 0, Improvement.CAMP, "scoring"),
    SHEEP("sheep", 2, 0, 0, Improvement.PASTURE, "scoring"),
    WHEAT("wheat", 1, 0, 0, Improvement.FARM, "scoring"),
    COAL("coal", 0, 1, 0, Improvement.MINE, "strategic"),
    HORSE("horse", 0, 1, 0, Improvement.PASTURE, "strategic"),
    IRON("iron", 0, 1, 0, Improvement.MINE, "strategic"),
    COTTON("cotton", 0, 0, 2, Improvement.PASTURE, "luxury"),
    COLOR("color", 0, 0, 2, Improvement.PASTURE, "luxury"),
    FUR("fur", 0, 0, 2, Improvement.CAMP, "luxury"),
    GEM("gem", 0,0, 3,Improvement.MINE, "luxury"),
    GOLD("gold", 0, 0, 2, Improvement.MINE, "luxury"),
    FUMIGATION("fumigation", 0, 0, 2, Improvement.PLANTATION, "luxury"),
    TUSK("tusk", 0, 0, 2, Improvement.CAMP, "luxury"),
    MARBLE("marble", 0, 0, 2, Improvement.MINE, "luxury"),
    SILK("silk", 0, 0, 2, Improvement.PLANTATION, "luxury"),
    SILVER("silver", 0, 0, 2, Improvement.MINE, "luxury"),
    SUGAR("sugar", 0, 0, 2, Improvement.PLANTATION, "luxury");

    public final String name;
    public final int food;
    public final int production;
    public final int gold;
    public final Improvement requiredImprovement;

    /*
     1. scoring (can not be exchanged with other civilizations)
     2. luxury (add 4 points to the civilization's happiness (only for the first time))
     3. strategic (require a technology to be shown on the screen / if appropriate improvement applied on a hex that includes this,
        some of this resource will be gained and can be used to build units and buildings / after removing a unit or a building ,
        the used resources will be return to the civilization)
    */
    public final String type;

    Resource(String name, int food, int production, int gold, Improvement requiredImprovement, String type) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
        this.requiredImprovement = requiredImprovement;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
