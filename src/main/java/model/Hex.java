package model;

import enums.*;
import model.unit.*;

import java.util.HashMap;
import java.util.logging.SocketHandler;

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
    private HexVisibility hexVisibility = HexVisibility.FOG_OF_WAR;
    private HashMap<Character, Integer> coordinatesInArray = new HashMap<>();
    private HashMap<Character, Integer> coordinatesInMap = new HashMap<>();
    private MilitaryUnit militaryUnit;
    private CivilUnit civilUnit;
    private City city=null;

    public Hex(Terrain terrain, Feature feature, Resource resource, boolean hasRiver, int x, int y) {
        this.terrain = terrain;
        this.feature = feature;
        this.resource = resource;
        this.hasRiver = hasRiver;
        this.coordinatesInArray.put('x', x);
        this.coordinatesInArray.put('y', y);
        this.coordinatesInMap.put('x', x * 2 + y % 2);
        this.coordinatesInMap.put('y', y);
        this.movementPrice = calculateMovementPrice();
    }

    public Hex clone() {
        Hex newHex;
        if (resource.type.equals("strategic")) {
            newHex = new Hex(terrain, feature, Resource.NULL, hasRiver, coordinatesInArray.get('x'), coordinatesInArray.get('y'));
        } else {
            newHex = new Hex(terrain, feature, resource, hasRiver, coordinatesInArray.get('x'), coordinatesInArray.get('y'));
        }
        newHex.owner = this.owner;
        newHex.improvement = this.improvement;
        newHex.hasDestroyedImprovement = this.hasDestroyedImprovement;
        newHex.percentOfBuildingImprovement = this.percentOfBuildingImprovement;
        newHex.isAnyCitizenWorking = this.isAnyCitizenWorking;
        newHex.hasRoad = this.hasRoad;
        newHex.hasRailRoad = this.hasRailRoad;
        newHex.militaryUnit = this.militaryUnit;
        newHex.civilUnit = this.civilUnit;
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
    private int calculateMovementPrice() {
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

    public int getMovementPrice() {
        return movementPrice;
    }

    public void setMovementPrice(int movementPrice) {
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

    public Unit getUnMilitaryUnit() {
        for (int i = 0; i < Game.getGame().getCivilizations().size(); i++) {
            for (int j = 0; j < Game.getGame().getCivilizations().get(i).getUnits().size(); j++) {
                if (Game.getGame().getCivilizations().get(i).getUnits().get(j).getCoordinatesInMap().get('x') == this.getCoordinatesInMap().get('x') &&
                        Game.getGame().getCivilizations().get(i).getUnits().get(j).getCoordinatesInMap().get('y') == this.getCoordinatesInMap().get('y') &&
                        Game.getGame().getCivilizations().get(i).getUnits().get(j) instanceof CivilUnit) {
                    return Game.getGame().getCivilizations().get(i).getUnits().get(j);
                }

            }
        }
        return null;
    }

    public Unit getMilitary() {
        for (int i = 0; i < Game.getGame().getCivilizations().size(); i++) {
            for (int j = 0; j < Game.getGame().getCivilizations().get(i).getUnits().size(); j++) {
                if (Game.getGame().getCivilizations().get(i).getUnits().get(j).getCoordinatesInMap().get('x') == this.getCoordinatesInMap().get('x') &&
                        Game.getGame().getCivilizations().get(i).getUnits().get(j).getCoordinatesInMap().get('y') == this.getCoordinatesInMap().get('y') &&
                        Game.getGame().getCivilizations().get(i).getUnits().get(j) instanceof MilitaryUnit) {
                    return Game.getGame().getCivilizations().get(i).getUnits().get(j);
                }

            }
        }
        return null;
    }
}

