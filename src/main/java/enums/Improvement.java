package enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum Improvement {
    Null("null", 0,0,0,new ArrayList<>(),new ArrayList<>()),
    CAMP("camp", 0, 0, 0,
            new ArrayList<>(Arrays.asList(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL)), new ArrayList<>(Arrays.asList(Feature.JUNGLE))),
    FARM("farm", 1, 0, 0,
            new ArrayList<>(Arrays.asList(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND)), new ArrayList<>()),
    LUMBER_MILL("lumber mill", 0, 0, 1,
            new ArrayList<>(), new ArrayList<>(Arrays.asList(Feature.JUNGLE))),
    MINE("mine", 1, 0, 1,
            new ArrayList<>(Arrays.asList(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND, Terrain.TUNDRA, Terrain.SNOWLAND, Terrain.HILL)), new ArrayList<>(Arrays.asList(Feature.JUNGLE, Feature.DENSE_FOREST, Feature.SWAMP))),
    PASTURE("pasture", 0, 0, 0,
            new ArrayList<>(Arrays.asList(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL, Terrain.GRASSLAND, Terrain.DESERT)), new ArrayList<>()),
    PLANTATION("plantation", 0, 0, 0,
            new ArrayList<>(Arrays.asList(Terrain.DESERT, Terrain.PLAIN, Terrain.GRASSLAND)), new ArrayList<>(Arrays.asList(Feature.JUNGLE, Feature.DENSE_FOREST, Feature.SWAMP, Feature.FLAT))),
    QUARRY("quarry", 0, 0, 0,
            new ArrayList<>(Arrays.asList(Terrain.TUNDRA, Terrain.PLAIN, Terrain.HILL, Terrain.DESERT, Terrain.GRASSLAND)), new ArrayList<>()),
    TRADING_POST("trading post", 0, 1, 0,
            new ArrayList<>(Arrays.asList(Terrain.TUNDRA, Terrain.PLAIN, Terrain.DESERT, Terrain.GRASSLAND)), new ArrayList<>()),
    FACTORY("factory", 0, 0, 2,
            new ArrayList<>(Arrays.asList(Terrain.TUNDRA, Terrain.PLAIN, Terrain.DESERT, Terrain.GRASSLAND, Terrain.SNOWLAND)), new ArrayList<>()),
    ROAD("road", 0, 0, 0, new ArrayList<>(), new ArrayList<>()),
    RAILROAD("railroad", 0, 0, 0, new ArrayList<>(), new ArrayList<>());

    public final String name;
    public final int food;
    public final int gold;
    public final int production;
    public final ArrayList<Terrain> terrains;
    public final ArrayList<Feature> features;

    Improvement(String name, int food, int gold, int production, ArrayList<Terrain> terrains, ArrayList<Feature> features) {
        this.name = name;
        this.food = food;
        this.gold = gold;
        this.production = production;
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
