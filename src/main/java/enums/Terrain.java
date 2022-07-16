package enums;

import javafx.scene.image.Image;

/**
 * @author Parsa
 */
public enum Terrain {
    // movement price -1 means that units cannot pass by this hex
    DESERT("desert", 0, 0, 0, -33, 1, "/tiles/Arid_Clear05.png"),
    GRASSLAND("grassland", 2, 0, 0, -33, 1, "/tiles/Grass1.png"),
    HILL("hill", 0, 2, 0, 25, 2, "/tiles/Hills2.png"),
    MOUNTAIN("mountain", 0, 0, 0, 25, -1, "/tiles/Mountains1.png"),
    OCEAN("ocean", 0, 0, 0, 25, -1, "/tiles/Ocean1.png"),
    PLAIN("plain", 1, 1, 0, -33, 1, "/tiles/Brambles5.png"),
    SNOWLAND("snowland", 0, 0, 0, -33, 1, "/tiles/SnowLandClear05.png"),
    TUNDRA("tundra", 1, 0, 0, -33, 1, "/tiles/SnowLandTrees01.png");

    public final String name;
    public final int food;
    public final int production;
    public final int gold;
    public final int combatEffect;
    public final int movementPrice;
    public final Image image;

    Terrain(String name, int food, int production, int gold, int combatEffect, int movementPrice, String imagePath) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatEffect = combatEffect;
        this.movementPrice = movementPrice;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    @Override
    public String toString() {
        return name;
    }
}
