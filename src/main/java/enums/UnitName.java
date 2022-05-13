package enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitName {

    NULL("null", 0, "null", 0, 0, 0, 0, null),
    WORKER("worker", 70, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>()),
    SETTLER("settler", 89, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>()),
    ARCHER("archer", 70, "Archery", 4, 6, 2, 2,
            new ArrayList<Resource>()),
    CHARIOTARCHER("chariotarcher", 60, "Mounted", 3, 6, 2, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    SCOUT("scout", 25, "Recon", 4, 0, 1, 2,
            new ArrayList<Resource>()),
    SPEARMAN("spearman", 50, "Melee", 7, 0, 1, 2,
            new ArrayList<Resource>()),
    WARRIOR("warrior", 40, "Melee", 6, 0, 1, 2,
            new ArrayList<Resource>()),
    CATAPULT("catapult", 100, "Siege", 4, 14, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    HORSEMAN("horseman", 80, "Mounted", 12, 0, 1, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    SWORDSMAN("swordsman", 80, "Melee", 11, 0, 1, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    CROSSBOWMAN("crossbowman", 120, "Archery", 6, 12, 2, 2,
            new ArrayList<Resource>()),
    KNIGHT("knight", 150, "Mounted", 18, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    LONGSWORDSMAN("longswordsman", 150, "Melee", 18, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    PIKEMAN("pikeman", 100, "Melee", 10, 0, 1, 2,
            new ArrayList<Resource>()),
    TREBUCHET("trebuchet", 170, "Siege", 6, 20, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON))),
    CANON("canon", 250, "Siege", 10, 26, 2, 2,
            new ArrayList<Resource>()),
    CAVALRY("cavalry", 260, "Mounted", 25, 0, 1, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    LANCER("lancer", 220, "Mounted", 22, 0, 1, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE))),
    MUSKETMAN("musketman", 120, "Gunpowder", 16, 0, 1, 2,
            new ArrayList<Resource>()),
    RIFLEMAN("rifleman", 200, "Gunpowder", 25, 0, 1, 2,
            new ArrayList<Resource>()),
    ANTITANKGUN("antitankgun", 300, "Gunpowder", 32, 0, 1, 2,
            new ArrayList<Resource>()),
    ARTILLERY("artillery", 420, "Siege", 16, 32, 3, 2,
            new ArrayList<Resource>()),
    INFANTRY("infantry", 300, "Gunpowder", 36, 0, 1, 2,
            new ArrayList<Resource>()),
    PANZER("panzer", 450, "Armored", 60, 0, 1, 5,
            new ArrayList<Resource>()),
    TANK("tank", 450, "Armored", 50, 0, 1, 4,
            new ArrayList<Resource>()),
    CITYUNIT("cityunit" ,0,"city",14,14,2,0, new ArrayList<Resource>());

    private final String name;
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

    public String getName() {
        return name;
    }

    public ArrayList<Resource> getResources() {
        return resources;
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
            , int movement, ArrayList<Resource> Resources) {
        this.name = name;
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resources = Resources;
    }
}