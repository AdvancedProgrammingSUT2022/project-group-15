package enums;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Parsa
 */
public enum Technology {
    AGRICULTURE("agriculture", 20, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.FARM)), new ArrayList<>()),
    ANIMAL_HUSBANDRY("animal husbandry", 35, new ArrayList<>(Arrays.asList(Resource.HORSE)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PASTURE)), new ArrayList<>(Arrays.asList(AGRICULTURE))),
    ARCHERY("archery", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARCHER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE))),
    MINING("mining", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.MINE)), new ArrayList<>(Arrays.asList(AGRICULTURE))),
    BRONZE_WORKING("bronze working", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.SPEARMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(MINING))),
    POTTERY("pottery", 35, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(AGRICULTURE))),
    CALENDAR("calendar", 70, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.PLANTATION)), new ArrayList<>(Arrays.asList(POTTERY))),
    MASONRY("masonry", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.QUARRY)), new ArrayList<>(Arrays.asList(MINING))),
    WHEEL("wheel", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CHARIOT_ARCHER)), new ArrayList<>(Arrays.asList(Improvement.ROAD)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY))),
    TRAPPING("trapping", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.TRADING_POST, Improvement.CAMP)), new ArrayList<>(Arrays.asList(ANIMAL_HUSBANDRY))),
    WRITING("writing", 55, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(POTTERY))),
    CONSTRUCTION("construction", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MASONRY))),
    HORSEBACK_RIDING("horseback riding", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.HORSEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL))),
    IRON_WORKING("iron working", 150, new ArrayList<>(Arrays.asList(Resource.IRON)), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.SWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(BRONZE_WORKING))),
    MATHEMATICS("mathematics", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CATAPULT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(WHEEL, ARCHERY))),
    PHILOSOPHY("philosophy", 100, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(WRITING))),
    CIVIL_SERVICE("civil service", 400, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.PIKEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHILOSOPHY, TRAPPING))),
    CURRENCY("currency", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS))),
    CHIVALRY("chivalry", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.KNIGHT)), new ArrayList<>(), new ArrayList<>(Arrays.asList(CIVIL_SERVICE, HORSEBACK_RIDING, CURRENCY))), // what is CAMEL ARCHER ?
    THEOLOGY("theology", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CALENDAR, PHILOSOPHY))),
    EDUCATION("education", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(THEOLOGY))),
    ENGINEERING("engineering", 250, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MATHEMATICS, CONSTRUCTION))),
    MACHINERY("machinery", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CROSSBOWMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ENGINEERING))),    // 1.2 faster road movement
    METAL_CASTING("metal casting", 240, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(IRON_WORKING))),
    PHYSICS("physics", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.TREBUCHET)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING, ENGINEERING))),
    STEEL("steel", 440, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LONGSWORDSMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METAL_CASTING))),
    ACOUSTICS("acoustics", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION))),
    ARCHAEOLOGY("archaeology", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS))),
    BANKING("banking", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(EDUCATION, CHIVALRY))),
    GUNPOWDER("gunpowder", 680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.MUSKETMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(PHYSICS, STEEL))),
    CHEMISTRY("chemistry", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER))),                                        // iron works
    PRINTING_PRESS("printing press", 650, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(MACHINERY, PHYSICS))),
    ECONOMICS("economics", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BANKING, PRINTING_PRESS))),
    FERTILIZER("fertilizer", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(CHEMISTRY))),                                     // Farms without Fresh Water yield increased by 1
    METALLURGY("metallurgy", 900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.LANCER)), new ArrayList<>(), new ArrayList<>(Arrays.asList(GUNPOWDER))),
    MILITARY_SCIENCE("military science", 1300, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.CAVALRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(ECONOMICS, CHEMISTRY))),
    RIFLING("rifling", 1425, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.RIFLEMAN)), new ArrayList<>(), new ArrayList<>(Arrays.asList(METALLURGY))),
    SCIENTIFIC_THEORY("scientific theory", 1300, new ArrayList<>(Arrays.asList(Resource.COAL)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ACOUSTICS))),
    BIOLOGY("biology", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ARCHAEOLOGY, SCIENTIFIC_THEORY))),
    STEAM_POWER("steam power", 1680, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.FACTORY)), new ArrayList<>(Arrays.asList(SCIENTIFIC_THEORY, MILITARY_SCIENCE))),
    RAILROAD("railroad", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(Improvement.RAILROAD)), new ArrayList<>(Arrays.asList(STEAM_POWER))),
    DYNAMITE("dynamite", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ARTILLERY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(FERTILIZER, RIFLING))),
    ELECTRICITY("electricity", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(BIOLOGY, STEAM_POWER))),
    REPLACEABLE_PARTS("replaceable parts", 1900, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(UnitName.ANTITANK_GUN, UnitName.INFANTRY)), new ArrayList<>(), new ArrayList<>(Arrays.asList(STEAM_POWER))),
    COMBUSTION("combustion", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(REPLACEABLE_PARTS, RAILROAD, DYNAMITE))),
    RADIO("radio", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY))),
    TELEGRAPH("telegraph", 2200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(ELECTRICITY)));

    public final String name;
    public final int cost;
    public final ArrayList<Resource> openingResources;
    public final ArrayList<Feature> openingFeatures;
    public final ArrayList<UnitName> openingUnits;
    public final ArrayList<Improvement> openingImprovements;
    public final ArrayList<Technology> prerequisiteTechnologies;
    // TODO : not Phase 1 : a final array list named openingBuildings

    Technology(String name, int cost, ArrayList<Resource> openingResources, ArrayList<Feature> openingFeatures, ArrayList<UnitName> openingUnits, ArrayList<Improvement> openingImprovements, ArrayList<Technology> prerequisiteTechnologies) {
        this.name = name;
        this.cost = cost;
        this.openingResources = openingResources;
        this.openingFeatures = openingFeatures;
        this.openingUnits = openingUnits;
        this.openingImprovements = openingImprovements;
        this.prerequisiteTechnologies = prerequisiteTechnologies;
    }

    @Override
    public String toString() {
        return "name : " + this.name + " - price : " + this.cost;
    }
}
