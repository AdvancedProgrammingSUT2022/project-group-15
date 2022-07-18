package enums;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Parsa
 */
public enum Technology {
    AGRICULTURE("agriculture", 20, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.FARM)), new ArrayList<>(), new ArrayList<>()),
    ANIMAL_HUSBANDRY("animal husbandry", 35, new ArrayList<>(Arrays.asList(Resource.HORSE)), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PASTURE)), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>()),
    ARCHERY("archery", 35, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARCHER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>()),
    MINING("mining", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.MINE)), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>()),
    BRONZE_WORKING("bronze working", 55, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.SPEARMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(MINING)), new ArrayList<>(Arrays.asList(Building.BARRACKS))),
    POTTERY("pottery", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE)), new ArrayList<>(Arrays.asList(Building.GRANARY))),
    CALENDAR("calendar", 70, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PLANTATION)), new ArrayList<>(Arrays.asList(POTTERY)), new ArrayList<>()),
    MASONRY("masonry", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.QUARRY)), new ArrayList<>(Arrays.asList(MINING)), new ArrayList<>(Arrays.asList(Building.WALLS))),
    WHEEL("wheel", 55, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CHARIOTARCHER)), new ArrayList<>(Arrays.asList(Improvement.ROAD)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY)), new ArrayList<>(Arrays.asList(Building.WATERMILL))),
    TRAPPING("trapping", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.TRADING_POST, Improvement.CAMP)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY)), new ArrayList<>()),
    WRITING("writing", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(POTTERY)), new ArrayList<>(Arrays.asList(Building.LIBRARY))),
    CONSTRUCTION("construction", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MASONRY)), new ArrayList<>(Arrays.asList(Building.COLOSSEUM))),
    HORSEBACK_RIDING("horseback riding", 100, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.HORSEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL)), new ArrayList<>(Arrays.asList(Building.STABLE, Building.CIRCUS))),
    IRON_WORKING("iron working", 150, new ArrayList<>(Arrays.asList(Resource.IRON)), new ArrayList<>(Arrays.asList(UnitName.SWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(BRONZE_WORKING)), new ArrayList<>(Arrays.asList(Building.ARMORY))),
    MATHEMATICS("mathematics", 100, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CATAPULT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL, ARCHERY)), new ArrayList<>(Arrays.asList(Building.COURTHOUSE))),
    PHILOSOPHY("philosophy", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(WRITING)), new ArrayList<>(Arrays.asList(Building.BURIALTOMB, Building.TEMPLE))),
    CIVIL_SERVICE("civil service", 400, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.PIKEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHILOSOPHY, TRAPPING)), new ArrayList<>()),
    CURRENCY("currency", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS)), new ArrayList<>(Arrays.asList(Building.MARKET))),
    CHIVALRY("chivalry", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.KNIGHT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(CIVIL_SERVICE, HORSEBACK_RIDING, CURRENCY)), new ArrayList<>(Arrays.asList(Building.CASTLE))), // what is CAMEL ARCHER ?
    THEOLOGY("theology", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CALENDAR, PHILOSOPHY)), new ArrayList<>(Arrays.asList(Building.MONASTERY, Building.GARDEN))),
    EDUCATION("education", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(THEOLOGY)), new ArrayList<>(Arrays.asList(Building.UNIVERSITY))),
    ENGINEERING("engineering", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS, CONSTRUCTION)), new ArrayList<>()),
    MACHINERY("machinery", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CROSSBOWMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ENGINEERING)), new ArrayList<>()),    // 1.2 faster road movement
    METAL_CASTING("metal casting", 240, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(IRON_WORKING)), new ArrayList<>(Arrays.asList(Building.FORGE, Building.WORKSHOP))),
    PHYSICS("physics", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.TREBUCHET)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING, ENGINEERING)), new ArrayList<>()),
    STEEL("steel", 440, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LONGSWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING)), new ArrayList<>()),
    ACOUSTICS("acoustics", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION)), new ArrayList<>()),
    ARCHAEOLOGY("archaeology", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS)), new ArrayList<>(Arrays.asList(Building.MUSEUM))),
    BANKING("banking", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION, CHIVALRY)), new ArrayList<>(Arrays.asList(Building.SATRAPCOURT, Building.BANK))),
    GUNPOWDER("gunpowder", 680, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.MUSKETMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHYSICS, STEEL)), new ArrayList<>()),
    CHEMISTRY("chemistry", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER)), new ArrayList<>()),                                        // iron works
    PRINTING_PRESS("printing press", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MACHINERY, PHYSICS)), new ArrayList<>(Arrays.asList(Building.THEATER))),
    ECONOMICS("economics", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BANKING, PRINTING_PRESS)), new ArrayList<>(Arrays.asList(Building.WINDMILL))),
    FERTILIZER("fertilizer", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CHEMISTRY)), new ArrayList<>()),                                     // Farms without Fresh Water yield increased by 1
    METALLURGY("metallurgy", 900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LANCER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER)), new ArrayList<>()),
    MILITARY_SCIENCE("military science", 1300, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CAVALRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ECONOMICS, CHEMISTRY)), new ArrayList<>(Arrays.asList(Building.MILITARYACADEMY))),
    RIFLING("rifling", 1425, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.RIFLEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METALLURGY)), new ArrayList<>()),
    SCIENTIFIC_THEORY("scientific theory", 1300, new ArrayList<>(Arrays.asList(Resource.COAL)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS)), new ArrayList<>(Arrays.asList(Building.PUBLICSCHOOL))),
    BIOLOGY("biology", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ARCHAEOLOGY, SCIENTIFIC_THEORY)), new ArrayList<>()),
    STEAM_POWER("steam power", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(SCIENTIFIC_THEORY, MILITARY_SCIENCE)), new ArrayList<>(Arrays.asList(Building.FACTORY))),
    RAILROAD("railroad", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.RAILROAD)), new ArrayList<>(Arrays.asList(STEAM_POWER)), new ArrayList<>(Arrays.asList(Building.ARSENAL))),
    DYNAMITE("dynamite", 1900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARTILLERY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(FERTILIZER, RIFLING)), new ArrayList<>()),
    ELECTRICITY("electricity", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BIOLOGY, STEAM_POWER)), new ArrayList<>(Arrays.asList(Building.STOCKEXCHANGE))),
    REPLACEABLE_PARTS("replaceable parts", 1900, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ANTITANKGUN, UnitName.INFANTRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(STEAM_POWER)), new ArrayList<>()),
    COMBUSTION("combustion", 2200, new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.TANK, UnitName.PANZER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(REPLACEABLE_PARTS, RAILROAD, DYNAMITE)), new ArrayList<>()),
    RADIO("radio", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY)), new ArrayList<>(Arrays.asList(Building.BROADCASTTOWER))),
    TELEGRAPH("telegraph", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY)), new ArrayList<>(Arrays.asList(Building.MILITARYBASE)));

    public final String name;
    public final int cost;
    public final ArrayList<Resource> openingResources;
    public final ArrayList<UnitName> openingUnits;
    public final ArrayList<Improvement> openingImprovements;
    public final ArrayList<Technology> prerequisiteTechnologies;
    public final ArrayList<Building> openingBuildings;
    public final Image image;

    Technology(String name, int cost, ArrayList<Resource> openingResources, ArrayList<UnitName> openingUnits, ArrayList<Improvement> openingImprovements, ArrayList<Technology> prerequisiteTechnologies, ArrayList<Building> openingBuildings) {
        this.name = name;
        this.cost = cost;
        this.openingResources = openingResources;
        this.openingUnits = openingUnits;
        this.openingImprovements = openingImprovements;
        this.prerequisiteTechnologies = prerequisiteTechnologies;
        this.openingBuildings = openingBuildings;
        this.image = new Image(getClass().getResource("/Technologies/" + this.name + ".png").toExternalForm());
    }

    @Override
    public String toString() {
        return "name : " + this.name + " - price : " + this.cost;
    }
}
