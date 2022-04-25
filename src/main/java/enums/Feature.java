package enums;

/**
 * @author Parsa
 */
public enum Feature {
    // movement price -1 means that units cannot pass by this hex
    // movement price -2 means that units should spend all of their remained moves to pass by this hex
    FLAT("flat", 2, 0, 0, -33, 1),
    JUNGLE("jungle", 1, 1, 0, 25, 2),
    ICE("ice", 0, 0, 0, 0, -1),
    DENSE_FOREST("dense forest", 1, -1, 0, 25, 2),
    SWAMP("swamp", -1, 0, 0, -33, 2),
    OASIS("oasis", 3, 0, 1, -33, 1),
    RIVER("river", 0, 0, 0, 0, -2),
    NULL("null",0,0,0,0,0);

    public final String name;
    public final int food;
    public final int production;
    public final int gold;
    public final int combatEffect;
    public final int movementPrice;

    Feature(String name, int food, int production, int gold, int combatEffect, int movementPrice) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatEffect = combatEffect;
        this.movementPrice = movementPrice;
    }

    @Override
    public String toString() {
        return name;
    }
}
