package enums;

/**
 * @author Parsa
 */
public enum Resource {
    NULL("null",0,0,0,"null"),
    BANANA("banana", 1, 0, 0, "scoring"),
    COW("cow", 1, 0, 0, "scoring"),
    GAZELLE("gazelle", 1, 0, 0, "scoring"),
    SHEEP("sheep", 2, 0, 0, "scoring"),
    WHEAT("wheat", 1, 0, 0, "scoring"),
    COAL("coal", 0, 1, 0,   "strategic"),
    HORSE("horse", 0, 1, 0, "strategic"),
    IRON("iron", 0, 1, 0,   "strategic"),
    COTTON("cotton", 0, 0, 2, "luxury"),
    COLOR("color", 0, 0, 2, "luxury"),
    FUR("fur", 0, 0, 2, "luxury"),
    GEM("gem", 0,0, 3, "luxury"),
    GOLD("gold", 0, 0, 2, "luxury"),
    FUMIGATION("fumigation", 0, 0, 2, "luxury"),
    TUSK("tusk", 0, 0, 2, "luxury"),
    MARBLE("marble", 0, 0, 2, "luxury"),
    SILK("silk", 0, 0, 2, "luxury"),
    SILVER("silver", 0, 0, 2, "luxury"),
    SUGAR("sugar", 0, 0, 2, "luxury");

    public final String name;
    public final int food;
    public final int production;
    public final int gold;

    /*
     1. scoring (can not be exchanged with other civilizations)
     2. luxury (add 4 points to the civilization's happiness (only for the first time))
     3. strategic (require a technology to be shown on the screen / if appropriate improvement applied on a hex that includes this,
        some of this resource will be gained and can be used to build units and buildings / after removing a unit or a building ,
        the used resources will be return to the civilization)
    */
    public final String type;

    Resource(String name, int food, int production, int gold, String type) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
