package enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitName {
    WORKER(70, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>()),
    SETTLER(89, "Civilian", 0, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>()),
    ARCHER(70, "Archery", 4, 6, 2, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.ARCHERY))),
    CHARIOTARCHER(60, "Mounted", 3, 6, 2, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE)), new ArrayList<Technology>(Arrays.asList(Technology.WHEEL))),
    SCOUT(25, "Recon", 4, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>()),
    SPEARMAN(50, "Melee", 7, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.BRONZE_WORKING))),
    WARRIOR(40, "Melee", 6, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>()),
    CATAPULT(100, "Siege", 4, 14, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON)), new ArrayList<Technology>(Arrays.asList(Technology.MATHEMATICS))),
    HORSEMAN(80, "Mounted", 12, 0, 0, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE)), new ArrayList<Technology>(Arrays.asList(Technology.HORSEBACK_RIDING))),
    SWORDSMAN(80, "Melee", 11, 0, 0, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON)), new ArrayList<Technology>(Arrays.asList(Technology.IRON_WORKING))),
    CROSSBOWMAN(120, "Archery", 6, 12, 2, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.MACHINERY))),
    KNIGHT(150, "Mounted", 18, 0, 0, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE)), new ArrayList<Technology>(Arrays.asList(Technology.CHIVALRY))),
    LONGSWORDSMAN(150, "Melee", 18, 0, 0, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON)), new ArrayList<Technology>(Arrays.asList(Technology.STEEL))),
    PIKEMAN(100, "Melee", 10, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.CIVIL_SERVICE))),
    TREBUCHET(170, "Siege", 6, 20, 2, 2,
            new ArrayList<Resource>(Arrays.asList(Resource.IRON)), new ArrayList<Technology>(Arrays.asList(Technology.PHYSICS))),
    CANON(250, "Siege", 10, 26, 2, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.CHEMISTRY))),
    CAVALRY(260, "Mounted", 25, 0, 0, 3,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE)), new ArrayList<Technology>(Arrays.asList(Technology.MILITARY_SCIENCE))),
    LANCER(220, "Mounted", 22, 0, 0, 4,
            new ArrayList<Resource>(Arrays.asList(Resource.HORSE)), new ArrayList<Technology>(Arrays.asList(Technology.METALLURGY))),
    MUSKETMAN(120, "Gunpowder", 16, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.GUNPOWDER))),
    RIFLEMAN(200, "Gunpowder", 25, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.RIFLING))),
    ANTITANKGUN(300, "Gunpowder", 32, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.REPLACEABLE_PARTS))),
    ARTILLERY(420, "Siege", 16, 32, 3, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.DYNAMITE))),
    INFANTRY(300, "Gunpowder", 36, 0, 0, 2,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.REPLACEABLE_PARTS))),
    PANZER(450, "Armored", 60, 0, 0, 5,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.COMBUSTION))),
    TANK(450 , "Armored", 50, 0, 0, 4,
            new ArrayList<Resource>(), new ArrayList<Technology>(Arrays.asList(Technology.COMBUSTION)));

    private final int cost;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final int movement;
    private final String combatType;
    private final ArrayList<Resource> resources;
    private final ArrayList<Technology> technologies;


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

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    UnitName(int cost, String combatType, int combatStrength, int rangedCombatStrength, int range
            , int movement, ArrayList<Resource> Resources, ArrayList<Technology> Technologies) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.resources = Resources;
        this.technologies = Technologies;
    }
}