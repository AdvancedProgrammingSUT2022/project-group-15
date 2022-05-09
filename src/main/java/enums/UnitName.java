package enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitName {

    NULL(0, "null", 0, 0, 0, 0, null),
    WORKER(70, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>()),
    SETTLER(89, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>()),
    ARCHER(70, "Archery", 4, 6, 2, 2,
            new ArrayList<Resource>()),
    CHARIOTARCHER(60, "Mounted", 3, 6, 2, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    SCOUT(25, "Recon", 4, 0, 1, 2,
            new ArrayList<Resource>()),
    SPEARMAN(50, "Melee", 7, 0, 1, 2,
            new ArrayList<Resource>()),
    WARRIOR(40, "Melee", 6, 0, 1, 2,
            new ArrayList<Resource>()),
    CATAPULT(100, "Siege", 4, 14, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    HORSEMAN(80, "Mounted", 12, 0, 1, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    SWORDSMAN(80, "Melee", 11, 0, 1, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    CROSSBOWMAN(120, "Archery", 6, 12, 2, 2,
            new ArrayList<Resource>()),
    KNIGHT(150, "Mounted", 18, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    LONGSWORDSMAN(150, "Melee", 18, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    PIKEMAN(100, "Melee", 10, 0, 1, 2,
            new ArrayList<Resource>()),
    TREBUCHET(170, "Siege", 6, 20, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    CANON(250, "Siege", 10, 26, 2, 2,
            new ArrayList<Resource>()),
    CAVALRY(260, "Mounted", 25, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    LANCER(220, "Mounted", 22, 0, 1, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    MUSKETMAN(120, "Gunpowder", 16, 0, 1, 2,
            new ArrayList<Resource>()),
    RIFLEMAN(200, "Gunpowder", 25, 0, 1, 2,
            new ArrayList<Resource>()),
    ANTITANKGUN(300, "Gunpowder", 32, 0, 1, 2,
            new ArrayList<Resource>()),
    ARTILLERY(420, "Siege", 16, 32, 3, 2,
            new ArrayList<Resource>()),
    INFANTRY(300, "Gunpowder", 36, 0, 1, 2,
            new ArrayList<Resource>()),
    PANZER(450, "Armored", 60, 0, 1, 5,
            new ArrayList<Resource>()),
    TANK(450, "Armored", 50, 0, 1, 4,
            new ArrayList<Resource>());

    private final int cost;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final int movement;
    private final String combatType;
    private final ArrayList<Resource> resources;

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

    public ArrayList<Resource> getResources() {
        return resources;
    }

    UnitName(int cost, String combatType, int combatStrength, int rangedCombatStrength, int range
            , int movement, ArrayList<Resource> Resources) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resources = Resources;
    }
}