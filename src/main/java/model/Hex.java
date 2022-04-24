package model;

import enums.Feature;
import enums.Improvement;
import enums.Resource;
import enums.Terrain;

import java.util.HashMap;

public class Hex {
    private Civilization owner;
    private Terrain terrain;
    private Feature feature;
    private Resource resource;
    private Improvement improvement;
    private boolean hasDestroyedImprovement;
    private int percentOfBuildingImprovement;
    private boolean isAnyCitizenWorking;
    private int movementPrice;
    private boolean hasRiver;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private HashMap<Character, Integer> coordinates = new HashMap<>();
    private MilitaryUnit militaryUnit;
    private CivilUnit civilUnit;

    public Hex(Terrain terrain, Feature feature, Resource resource, boolean hasRiver, int x, int y) {
        this.terrain = terrain;
        this.feature = feature;
        this.resource = resource;
        this.hasRiver = hasRiver;
        this.coordinates.put('x', x);
        this.coordinates.put('y', y);
        this.movementPrice = calculateMovementPrice();
    }

    private int calculateMovementPrice() {
        // TODO: 4/24/2022
        return 0;
    }

    public MilitaryUnit getMilitaryUnit() {
        return militaryUnit;
    }

    public void setMilitaryUnit(MilitaryUnit militaryUnit) {
        this.militaryUnit = militaryUnit;
    }

    public CivilUnit getCivilUnit() {
        return civilUnit;
    }

    public void setCivilUnit(CivilUnit civilUnit) {
        this.civilUnit = civilUnit;
    }

    public HashMap<Character, Integer> getCoordinates() {
        return coordinates;
    }

    public int getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(int movementPrice) {
        this.movementPrice = movementPrice;
    }

    public boolean doesHaveRiver() {
        return hasRiver;
    }
}
