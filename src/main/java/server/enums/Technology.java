package server.enums;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Parsa
 */
public enum Technology {
    AGRICULTURE("agriculture", 20, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.FARM)), new ArrayList<>(), new ArrayList<>(), 10, 353),
    ANIMAL_HUSBANDRY("animal husbandry", 35, new ArrayList<>(Arrays.asList(Resource.HORSE)), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PASTURE)), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>(), 347, 288),
    ARCHERY("archery", 35, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARCHER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>(), 348, 417),
    MINING("mining", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.MINE)), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>(), 348, 545),
    BRONZE_WORKING("bronze working", 55, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.SPEARMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(MINING)), new ArrayList<>(Arrays.asList(Building.BARRACKS)), 695,611),
    POTTERY("pottery", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>(Arrays.asList(Building.GRANARY)), 348, 95),
    CALENDAR("calendar", 70, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PLANTATION)), new ArrayList<>(Arrays.asList(POTTERY)), new ArrayList<>(), 695, 95),
    MASONRY("masonry", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.QUARRY)), new ArrayList<>(Arrays.asList(MINING)), new ArrayList<>(Arrays.asList(Building.WALLS)), 695, 547),
    WHEEL("the wheel", 55, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CHARIOTARCHER)), new ArrayList<>(Arrays.asList(Improvement.ROAD)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY)), new ArrayList<>(Arrays.asList(Building.WATERMILL)), 695, 351),
    TRAPPING("trapping", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.TRADING_POST, Improvement.CAMP)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY)), new ArrayList<>(), 695, 223),
    WRITING("writing", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(POTTERY)), new ArrayList<>(Arrays.asList(Building.LIBRARY)), 695, 158),
    CONSTRUCTION("construction", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MASONRY)), new ArrayList<>(Arrays.asList(Building.COLOSSEUM)), 1043, 547),
    HORSEBACK_RIDING("horseback riding", 100, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.HORSEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL)), new ArrayList<>(Arrays.asList(Building.STABLE, Building.CIRCUS)), 1043, 287),
    IRON_WORKING("iron working", 150, new ArrayList<>(Arrays.asList(Resource.IRON)), new ArrayList<>(Arrays.asList(UnitName.SWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(BRONZE_WORKING)), new ArrayList<>(Arrays.asList(Building.ARMORY)), 1043, 611),
    MATHEMATICS("mathematics", 100, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CATAPULT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL, ARCHERY)), new ArrayList<>(Arrays.asList(Building.COURTHOUSE)), 1043, 417),
    PHILOSOPHY("philosophy", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(WRITING)), new ArrayList<>(Arrays.asList(Building.BURIAL_TOMB, Building.TEMPLE)), 1043, 159),
    CIVIL_SERVICE("civil service", 400, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.PIKEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHILOSOPHY, TRAPPING)), new ArrayList<>(), 1391, 223),
    CURRENCY("currency", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS)), new ArrayList<>(Arrays.asList(Building.MARKET)), 1391, 418),
    CHIVALRY("chivalry", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.KNIGHT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(CIVIL_SERVICE, HORSEBACK_RIDING, CURRENCY)), new ArrayList<>(Arrays.asList(Building.CASTLE)), 1739, 287), // what is CAMEL ARCHER ?
    THEOLOGY("theology", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CALENDAR, PHILOSOPHY)), new ArrayList<>(Arrays.asList(Building.MONASTERY, Building.GARDEN)), 1391, 94),
    EDUCATION("education", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(THEOLOGY)), new ArrayList<>(Arrays.asList(Building.UNIVERSITY)), 1739, 160),
    ENGINEERING("engineering", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS, CONSTRUCTION)), new ArrayList<>(), 1391, 483),
    MACHINERY("machinery", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CROSSBOWMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ENGINEERING)), new ArrayList<>(), 1739, 482),    // 1.2 faster road movement
    METAL_CASTING("metal casting", 240, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(IRON_WORKING)), new ArrayList<>(Arrays.asList(Building.FORGE, Building.WORKSHOP)), 1391, 612),
    PHYSICS("physics", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.TREBUCHET)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING, ENGINEERING)), new ArrayList<>(), 1739, 547),
    STEEL("steel", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LONGSWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING)), new ArrayList<>(), 1739, 611),
    ACOUSTICS("acoustics", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION)), new ArrayList<>(), 2086, 223),
    ARCHAEOLOGY("archaeology", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS)), new ArrayList<>(Arrays.asList(Building.MUSEUM)), 2782, 94),
    BANKING("banking", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION, CHIVALRY)), new ArrayList<>(Arrays.asList(Building.SATRAP_COURT, Building.BANK)), 2086, 352),
    GUNPOWDER("gunpowder", 680, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.MUSKETMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHYSICS, STEEL)), new ArrayList<>(), 2086, 611),
    CHEMISTRY("chemistry", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER)), new ArrayList<>(), 2434, 548),                                        // iron works
    PRINTING_PRESS("printing press", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MACHINERY, PHYSICS)), new ArrayList<>(Arrays.asList(Building.THEATER)), 2086, 482),
    ECONOMICS("economics", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BANKING, PRINTING_PRESS)), new ArrayList<>(Arrays.asList(Building.WINDMILL)), 2434, 352),
    FERTILIZER("fertilizer", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CHEMISTRY)), new ArrayList<>(), 2782, 547),                                     // Farms without Fresh Water yield increased by 1
    METALLURGY("metallurgy", 900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LANCER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER)), new ArrayList<>(), 2434, 611),
    MILITARY_SCIENCE("military science", 1300, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CAVALRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ECONOMICS, CHEMISTRY)), new ArrayList<>(Arrays.asList(Building.MILITARY_ACADEMY)), 2782, 352),
    RIFLING("rifling", 1425, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.RIFLEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METALLURGY)), new ArrayList<>(), 2782, 611),
    SCIENTIFIC_THEORY("scientific theory", 1300, new ArrayList<>(Arrays.asList(Resource.COAL)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS)), new ArrayList<>(Arrays.asList(Building.PUBLIC_SCHOOL)), 2782, 223),
    BIOLOGY("biology", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ARCHAEOLOGY, SCIENTIFIC_THEORY)), new ArrayList<>(), 3130, 223),
    STEAM_POWER("steam power", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(SCIENTIFIC_THEORY, MILITARY_SCIENCE)), new ArrayList<>(Arrays.asList(Building.FACTORY)), 3130, 352),
    RAILROAD("railroad", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.RAILROAD)), new ArrayList<>(Arrays.asList(STEAM_POWER)), new ArrayList<>(Arrays.asList(Building.ARSENAL)), 3478, 482),
    DYNAMITE("dynamite", 1900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARTILLERY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(FERTILIZER, RIFLING)), new ArrayList<>(), 3478, 547),
    ELECTRICITY("electricity", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BIOLOGY, STEAM_POWER)), new ArrayList<>(Arrays.asList(Building.STOCK_EXCHANGE)), 3478, 223),
    REPLACEABLE_PARTS("replaceable parts", 1900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ANTITANKGUN, UnitName.INFANTRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(STEAM_POWER)), new ArrayList<>(), 3478, 352),
    COMBUSTION("combustion", 2200, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.TANK, UnitName.PANZER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(REPLACEABLE_PARTS, RAILROAD, DYNAMITE)), new ArrayList<>(), 3826, 482),
    RADIO("radio", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY)), new ArrayList<>(Arrays.asList(Building.BROADCAST_TOWER)), 3826, 287),
    TELEGRAPH("telegraph", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY)), new ArrayList<>(Arrays.asList(Building.MILITARY_BASE)), 3826, 223);

    public final String name;
    public final int cost;
    public final ArrayList<Resource> openingResources;
    public final ArrayList<UnitName> openingUnits;
    public final ArrayList<Improvement> openingImprovements;
    public final ArrayList<Technology> prerequisiteTechnologies;
    public final ArrayList<Building> openingBuildings;
  //  public final Image image;
    public final int x;
    public final int y;

    Technology(String name, int cost, ArrayList<Resource> openingResources, ArrayList<UnitName> openingUnits, ArrayList<Improvement> openingImprovements, ArrayList<Technology> prerequisiteTechnologies, ArrayList<Building> openingBuildings, int x, int y) {
        this.name = name;
        this.cost = cost;
        this.openingResources = openingResources;
        this.openingUnits = openingUnits;
        this.openingImprovements = openingImprovements;
        this.prerequisiteTechnologies = prerequisiteTechnologies;
        this.openingBuildings = openingBuildings;
        this.x = x;
        this.y = y;
     //   this.image = new Image(getClass().getResource("/Technologies/" + this.name + ".png").toExternalForm());
    }

    public static Technology getTechnologyByName(String technologyName) {
        for (Technology technology : Technology.values()) {
            if (technology.name.equals(technologyName)) {
                return technology;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("name : " + this.name + "\ncost (science) : " + this.cost + "\n");
        if (!openingResources.isEmpty()) {
            result.append("opening resources : \n");
            for (Resource resource : openingResources) {
                result.append("\t").append(resource.name).append("\n");
            }
        }
        if (!openingUnits.isEmpty()) {
            result.append("opening units : \n");
            for (UnitName unitName : openingUnits) {
                result.append("\t").append(unitName.getName()).append("\n");
            }
        }
        if (!openingImprovements.isEmpty()) {
            result.append("opening improvements : \n");
            for (Improvement improvement : openingImprovements) {
                result.append("\t").append(improvement.name).append("\n");
            }
        }
        if (!openingBuildings.isEmpty()) {
            result.append("opening improvements : \n");
            for (Building building : openingBuildings) {
                result.append("\t").append(building.name).append("\n");
            }
        }
        return result.toString();
    }
}
