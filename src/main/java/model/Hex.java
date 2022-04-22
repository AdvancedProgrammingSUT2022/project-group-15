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

    //constructor
    public Hex() {

    }

    public Unit getUnitInside() {
        //return unit
        return null;
    }

}
