package enums;

import java.util.ArrayList;
import java.util.List;

public enum Improvement {
    CAMP("camp", 0, 0, 0, Technology.TRAPPING,
            new ArrayList<>(List.of(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL)), new ArrayList<>(List.of(Feature.JUNGLE))),
    FARM("farm", 1, 0, 0, Technology.AGRICULTURE,
            new ArrayList<>(List.of(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND)), new ArrayList<>()),
    LUMBER_MILL("lumber mill", 0, 0, 1, Technology.MASONRY,
            new ArrayList<>(), new ArrayList<>(List.of(Feature.JUNGLE))),
    MINE("mine", 1, 0, 1, Technology.MINING,
            new ArrayList<>(List.of(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND, Terrain.TUNDRA, Terrain.SNOWLAND, Terrain.HILL)), new ArrayList<>(List.of(Feature.JUNGLE, Feature.DENSE_FOREST, Feature.SWAMP))),
    PASTURE("pasture", 0, 0, 0, Technology.ANIMAL_HUSBANDRY,
            new ArrayList<>(List.of(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL, Terrain.GRASSLAND, Terrain.DESERT)), new ArrayList<>()),
    PLANTATION("plantation", 0, 0, 0, Technology.CALENDAR,
            new ArrayList<>(List.of(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND)), new ArrayList<>(List.of(Feature.JUNGLE, Feature.DENSE_FOREST, Feature.SWAMP, Feature.FLAT))),
    QUARRY("quarry", 0, 0, 0, Technology.MASONRY,
            new ArrayList<>(List.of(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL, Terrain.DESERT, Terrain.GRASSLAND)), new ArrayList<>()),
    TRADING_POST("trading post", 0, 1, 0, Technology.TRAPPING,
            new ArrayList<>(List.of(Terrain.TUNDRA, Terrain.PLAIN, Terrain.DESERT, Terrain.GRASSLAND)), new ArrayList<>()),
    FACTORY("factory", 0, 0, 2, Technology.ENGINEERING,
            new ArrayList<>(List.of(Terrain.TUNDRA, Terrain.PLAIN, Terrain.DESERT, Terrain.GRASSLAND, Terrain.SNOWLAND)), new ArrayList<>()),
    ROAD("road", 0, 0, 0, null, new ArrayList<>(), new ArrayList<>()),
    RAILROAD("railroad", 0, 0, 0, null, new ArrayList<>(), new ArrayList<>());

    public final String name;
    public final int food;
    public final int gold;
    public final int production;
    public final Technology technology;
    public final ArrayList<Terrain> terrains;
    private final ArrayList<Feature> features;

    Improvement(String name, int food, int gold, int production, Technology technology, ArrayList<Terrain> terrains, ArrayList<Feature> features) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
        this.technology = technology;
        this.terrains = terrains;
        this.features = features;
    }

    public static Improvement getImprovementByName(String improvementName) {
        for (Improvement improvement : Improvement.values()) {
            if (improvement.name.equals(improvementName)) {
                return improvement;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
