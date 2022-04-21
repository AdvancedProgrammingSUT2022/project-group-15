package enums;

/**
 * @author Parsa
 */
public enum Resource {
    BANANA("banana", 1, 0, 0),
    COW("cow", 1, 0, 0),
    GAZELLE("gazelle", 1, 0, 0),
    SHEEP("sheep", 2, 0, 0),
    WHEAT("wheat", 1, 0, 0),
    COAL("coal", 0, 1, 0),
    HORSE("horse", 0, 1, 0),
    IRON("iron", 0, 1, 0),
    COTTON("cotton", 0, 0, 2),
    COLOR("color", 0, 0, 2),
    FUR("fur", 0, 0, 2),
    GEM("gem", 0,0, 3),
    GOLD("gold", 0, 0, 2),
    FUMIGATION("fumigation", 0, 0, 2),
    TUSK("tusk", 0, 0, 2),
    MARBLE("marble", 0, 0, 2),
    SILK("silk", 0, 0, 2),
    SILVER("silver", 0, 0, 2),
    SUGAR("sugar", 0, 0, 2);

    public final String name;
    public final int food;
    public final int production;
    public final int gold;

    Resource(String name, int food, int production, int gold) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
