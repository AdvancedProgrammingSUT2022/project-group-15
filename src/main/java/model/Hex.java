package model;

import enums.*;
import model.unit.*;

import java.util.HashMap;

public class Hex {
    private boolean hasRuins;
    private Ruins ruins;
    private Civilization owner;
    private Terrain terrain;
    private Feature feature;
    private Resource resource;
    private Improvement improvement = Improvement.Null;
    private boolean hasDestroyedImprovement;
    private int percentOfBuildingImprovement;
    private boolean isAnyCitizenWorking = false;
    private double movementPrice;
    private boolean hasRiver;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private double defenceBonus;
    private HexVisibility hexVisibility = HexVisibility.FOG_OF_WAR;
    private HashMap<Character, Integer> coordinatesInArray = new HashMap<>();
    private HashMap<Character, Integer> coordinatesInMap = new HashMap<>();
    private MilitaryUnit militaryUnit;
    private CivilUnit civilUnit;
    private City city = null;

    public Hex(Terrain terrain, Feature feature, Resource resource, boolean hasRiver, int x, int y) {
        this.hasRuins = false;
        this.terrain = terrain;
        this.feature = feature;
        this.resource = resource;
        this.hasRiver = hasRiver;
        this.coordinatesInArray.put('x', x);
        this.coordinatesInArray.put('y', y);
        this.coordinatesInMap.put('x', x * 2 + y % 2);
        this.coordinatesInMap.put('y', y);
        this.movementPrice = calculateMovementPrice();
        this.defenceBonus = (100.0 - terrain.combatEffect - feature.combatEffect) / 100;
    }

    public Hex clone() {
        Hex newHex = new Hex(terrain, feature, resource, hasRiver, coordinatesInArray.get('x'), coordinatesInArray.get('y'));

        newHex.owner = this.owner;
        newHex.improvement = this.improvement;
        newHex.hasDestroyedImprovement = this.hasDestroyedImprovement;
        newHex.percentOfBuildingImprovement = this.percentOfBuildingImprovement;
        newHex.isAnyCitizenWorking = this.isAnyCitizenWorking;
        newHex.hasRoad = this.hasRoad;
        newHex.hasRailRoad = this.hasRailRoad;
        newHex.militaryUnit = this.militaryUnit;
        newHex.civilUnit = this.civilUnit;
        newHex.city = this.city;
        return newHex;
    }

    /**
     * calculates the net movement price of this hex. <br>
     * returns -1 if that units cannot pass by this hex <br>
     * returns -2 if that units should spend all of their remained moves to pass by this hex
     *
     * @return the final movement price of this hex
     * @author Parsa
     */
    public double calculateMovementPrice() {
        if (this.hasRailRoad)
            return 0.2;
        if (this.hasRoad)
            return 0.5;
        if (feature != Feature.NULL) {
            return feature.movementPrice;
        }
        return terrain.movementPrice;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getPercentOfBuildingImprovement() {
        return percentOfBuildingImprovement;
    }

    public void setPercentOfBuildingImprovement(int percentOfBuildingImprovement) {
        this.percentOfBuildingImprovement = percentOfBuildingImprovement;
    }

    public boolean hasRuins() {
        return hasRuins;
    }

    public void setHasRuins(boolean hasRuins) {
        this.hasRuins = hasRuins;
    }

    public Ruins getRuins() {
        return ruins;
    }

    public void setRuins(Ruins ruins) {
        this.ruins = ruins;
    }

    public HexVisibility getHexVisibility() {
        return hexVisibility;
    }

    public void setHexVisibility(HexVisibility hexVisibility) {
        this.hexVisibility = hexVisibility;
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

    public HashMap<Character, Integer> getCoordinatesInArray() {
        return coordinatesInArray;
    }

    public HashMap<Character, Integer> getCoordinatesInMap() {
        return coordinatesInMap;
    }

    public double getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(double movementPrice) {
        this.movementPrice = movementPrice;
    }

    public boolean doesHaveRiver() {
        return hasRiver;
    }

    public void setHasRiver(boolean hasRiver) {
        this.hasRiver = hasRiver;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public boolean isHasDestroyedImprovement() {
        return hasDestroyedImprovement;
    }

    public void setHasDestroyedImprovement(boolean hasDestroyedImprovement) {
        this.hasDestroyedImprovement = hasDestroyedImprovement;
    }

    public double getDefenceBonus() {
        return defenceBonus;
    }

    public boolean isAnyCitizenWorking() {
        return isAnyCitizenWorking;
    }

    public void setAnyCitizenWorking(boolean anyCitizenWorking) {
        isAnyCitizenWorking = anyCitizenWorking;
    }

    public boolean isHasRoad() {
        return hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public boolean isHasRailRoad() {
        return hasRailRoad;
    }

    public void setHasRailRoad(boolean hasRailRoad) {
        this.hasRailRoad = hasRailRoad;
    }
}

