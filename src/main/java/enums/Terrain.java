package enums;

import javafx.scene.image.Image;

/**
 * @author Parsa
 */
public enum Terrain {
    // movement price -1 means that units cannot pass by this hex
    DESERT("desert", 0, 0, 0, -33, 1),
    GRASSLAND("grassland", 2, 0, 0, -33, 1),
    HILL("hill", 0, 2, 0, 25, 2),
    MOUNTAIN("mountain", 0, 0, 0, 25, -1),
    OCEAN("ocean", 0, 0, 0, 25, -1),
    PLAIN("plain", 1, 1, 0, -33, 1),
    SNOWLAND("snowland", 0, 0, 0, -33, 1),
    TUNDRA("tundra", 1, 0, 0, -33, 1);

    public final String name;
    public final int food;
    public final int production;
    public final int gold;
    public final int combatEffect;
    public final int movementPrice;
    public final Image image;

    Terrain(String name, int food, int production, int gold, int combatEffect, int movementPrice) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatEffect = combatEffect;
        this.movementPrice = movementPrice;
        this.image = new Image(getClass().getResource("/tiles/" + this.name + ".png").toExternalForm());
    }

    @Override
    public String toString() {
        return name;
    }
}
