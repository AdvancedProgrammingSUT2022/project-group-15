package model;

import enums.*;

import java.util.ArrayList;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    private ArrayList<ArrayList<HexVisibility>> visibilityMap;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<UnitName> openedUnits = new ArrayList<>();
    private ArrayList<Resource> openedResources = new ArrayList<>();
    private City capital;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private int goldStorage = 0;
    private int scienceStorage = 0;
    private int sciencePerTurn = 0;
    private int happiness = 0;


    public Civilization(User user) {
        this.user = user;
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }
    // TODO: 4/20/2022  getmap()

    public void adjustVisibility() {

        for (int i = 0; i < visibilityMap.size(); i++) {
            for (int j = 0; j < visibilityMap.get(0).size(); j++) {
                if (visibilityMap.get(i).get(j) != HexVisibility.FOG_OF_WAR) {
                    visibilityMap.get(i).set(j, HexVisibility.DETERMINED);
                }
            }
        }
        adjustVisibilityUnits();
        adjustVisibilityCities();
    }

    private void adjustVisibilityCities() {
        for (City city : cities) {
            for (Hex cityHex : city.getCityHexes()) {
                seeNeighbors(cityHex.getCoordinates().get('x'), cityHex.getCoordinates().get('y'));
            }
        }
    }

    private void adjustVisibilityUnits() {

        for (Unit unit : units) {
            int x = unit.coordinates.get('x');
            int y = unit.coordinates.get('y');
            visibilityMap.get(x).set(y, HexVisibility.TRANSPARENT);
            seeNeighbors(x, y);
            for (NeighborHex neighborHex : NeighborHex.values()) {
                if (!(Game.getGame().map.get(x).get(y).getTerrain().name.equals(Terrain.HILL.name) ||
                        Game.getGame().map.get(x).get(y).getTerrain().name.equals(Terrain.MOUNTAIN.name) ||
                        Game.getGame().map.get(x).get(y).getFeature().name.equals(Feature.JUNGLE.name) ||
                        Game.getGame().map.get(x).get(y).getFeature().name.equals(Feature.DENSE_FOREST.name)))
                    seeNeighbors(x + neighborHex.xDiff, y + neighborHex.yDiff);
            }
        }
    }

    private void seeNeighbors(int x, int y) {
        for (NeighborHex neighborHex : NeighborHex.values()) {
            visibilityMap.get(x + neighborHex.xDiff).set(y + neighborHex.yDiff, HexVisibility.TRANSPARENT);
        }
    }

}
