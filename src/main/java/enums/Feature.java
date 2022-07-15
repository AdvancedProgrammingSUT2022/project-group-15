package enums;

import javafx.scene.image.Image;

/**
 * @author Parsa
 */
public enum Feature {
    // movement price -1 means that units cannot pass by this hex
    // movement price -2 means that units should spend all of their remained moves to pass by this hex
    FLAT("flat", 2, 0, 0, -33, 1, "/tiles/003Clear1.png"),
    JUNGLE("jungle", 1, 1, 0, 25, 2, "/tiles/Jungle4.png"),
    ICE("ice", 0, 0, 0, 0, -1, "/tiles/Ice1.png"),
    DENSE_FOREST("dense forest", 1, -1, 0, 25, 2, "/tiles/Woods4_1.png"),
    SWAMP("swamp", -1, 0, 0, -33, 2, "/tiles/Lake3.png"),
    OASIS("oasis", 3, 0, 1, -33, 1, "/tiles/Arid_Brush05.png"),
    RIVER("river", 0, 0, 0, 0, -2, "/tiles/River1-4.png"),
    NULL("null",0,0,0,0,0, "/tiles/003Clear1.png");

    public final String name;
    public final int food;
    public final int production;
    public final int gold;
    public final int combatEffect;
    public final int movementPrice;
    public final Image image;

    Feature(String name, int food, int production, int gold, int combatEffect, int movementPrice, String imagePath) {
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
