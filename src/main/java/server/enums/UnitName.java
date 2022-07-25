package server.enums;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public enum UnitName {

    NULL("null", 0, "null", 0, 0, 0, 0, Resource.NULL),
    WORKER("worker", 70, "Civilian", 0, 0, 0, 2, Resource.NULL),
    SETTLER("settler", 89, "Civilian", 0, 0, 0, 2, Resource.NULL),
    ARCHER("archer", 70, "Archery", 4, 6, 2, 2,
            Resource.NULL),
    CHARIOTARCHER("chariotarcher", 60, "Mounted", 3, 6, 2, 4, (Resource.HORSE)),
    SCOUT("scout", 25, "Recon", 4, 0, 1, 2,
            Resource.NULL),
    SPEARMAN("spearman", 50, "Melee", 7, 0, 1, 2,
            Resource.NULL),
    WARRIOR("warrior", 40, "Melee", 6, 0, 1, 2,
            Resource.NULL),
    CATAPULT("catapult", 100, "Siege", 4, 14, 2, 2,
            (Resource.IRON)),
    HORSEMAN("horseman", 80, "Mounted", 12, 0, 1, 4,
            Resource.HORSE),
    SWORDSMAN("swordsman", 80, "Melee", 11, 0, 1, 2,
            Resource.IRON),
    CROSSBOWMAN("crossbowman", 120, "Archery", 6, 12, 2, 2,
            Resource.NULL),
    KNIGHT("knight", 150, "Mounted", 18, 0, 1, 3,
            Resource.HORSE),
    LONGSWORDSMAN("longswordsman", 150, "Melee", 18, 0, 1, 3,
            Resource.IRON),
    PIKEMAN("pikeman", 100, "Melee", 10, 0, 1, 2,
            Resource.NULL),
    TREBUCHET("trebuchet", 170, "Siege", 6, 20, 2, 2,
            Resource.IRON),
    CANON("canon", 250, "Siege", 10, 26, 2, 2,
            Resource.NULL),
    CAVALRY("cavalry", 260, "Mounted", 25, 0, 1, 3,
            Resource.HORSE),
    LANCER("lancer", 220, "Mounted", 22, 0, 1, 4,
            Resource.HORSE),
    MUSKETMAN("musketman", 120, "Gunpowder", 16, 0, 1, 2,
            Resource.NULL),
    RIFLEMAN("rifleman", 200, "Gunpowder", 25, 0, 1, 2,
            Resource.NULL),
    ANTITANKGUN("antitankgun", 300, "Gunpowder", 32, 0, 1, 2,
            Resource.NULL),
    ARTILLERY("artillery", 420, "Siege", 16, 32, 3, 2,
            Resource.NULL),
    INFANTRY("infantry", 300, "Gunpowder", 36, 0, 1, 2,
            Resource.NULL),
    PANZER("panzer", 450, "Armored", 60, 0, 1, 5,
            Resource.NULL),
    TANK("tank", 450, "Armored", 50, 0, 1, 4, Resource.NULL),
    CITYUNIT("cityunit", 0, "city", 14, 14, 2, 1, Resource.NULL);

    private final String name;
    private final int cost;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final int movement;
    private final String combatType;
    private final Resource resource;
    private final Image image;

    public int getCost() {
        return cost;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public int getRange() {
        return range;
    }

    public int getMovement() {
        return movement;
    }

    public String getCombatType() {
        return combatType;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Resource getResource() {
        return resource;
    }

    public static UnitName getUnitNameByName(String unitName) {
        for (UnitName name : UnitName.values()) {
            if (unitName.equals(name.getName())) {
                return name;
            }
        }
        return null;
    }

    UnitName(String name, int cost, String combatType, int combatStrength, int rangedCombatStrength, int range
            , int movement, Resource Resource) {
        this.name = name;
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resource = Resource;
        if (name.equals("null") || name.equals("cityunit"))
            this.image = new WritableImage(40, 40);
        else
            this.image = new Image(getClass().getResource("/units/" + Character.toUpperCase(name.charAt(0)) +
                    name.substring(1) + "_(Civ5)" + ".png").toExternalForm());
    }
}