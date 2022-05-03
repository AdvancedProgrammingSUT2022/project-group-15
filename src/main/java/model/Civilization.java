package model;

import enums.*;
import model.unit.SettlerUnit;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.Random;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    private Map visibilityMap;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private ArrayList<Technology> availableTechnologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<UnitName> openedUnits = new ArrayList<>();
    private ArrayList<Resource> openedResources = new ArrayList<>();
    private ArrayList<Feature> openedFeatures = new ArrayList<>();
    private ArrayList<Improvement> openedImprovements = new ArrayList<>();
    private City capital;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private int goldStorage = 0;
    private int scienceStorage = 0;
    private int sciencePerTurn = 0;
    private int happiness = 0;


    public Civilization(User user) {
        this.user = user;
        visibilityMap = Game.getGame().map.clone();
    }

    public void nextTurn(){
        scienceStorage += sciencePerTurn;
        if(technologyInProgress != null && scienceStorage >= technologyInProgress.cost){
            openNewTechnology();
            updateAvailableTechnologies();
        }
        sciencePerTurn = calculateSciencePerTurn();
        goldStorage += calculateGoldPerTurn();
    }

    private void updateAvailableTechnologies() {
        availableTechnologies.clear();
        for (Technology tech : Technology.values()) {
            if(Game.getGame().getSelectedCivilization().getTechnologies().containsAll(tech.prerequisiteTechnologies))
                availableTechnologies.add(tech);
        }
    }

    private void openNewTechnology() {
        scienceStorage = 0;
        technologies.add(technologyInProgress);
        openedUnits.addAll(technologyInProgress.openingUnits);
        openedFeatures.addAll(technologyInProgress.openingFeatures);
        openedImprovements.addAll(technologyInProgress.openingImprovements);
        openedResources.addAll(technologyInProgress.openingResources);
        technologyInProgress = null;
    }

    private int calculateGoldPerTurn() {
        // TODO : implement
        return 0;
    }

    private int calculateSciencePerTurn() {
        int result = 0;
        for (City city : cities) {
            result += city.getNumberOfCitizen();
        }
        result += 3; // for capital
        return result;
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public User getUser() {
        return user;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }
    // TODO: 4/20/2022  getmap()

    public void adjustVisibility() {

        for (int i = 0; i < visibilityMap.map.size(); i++) {
            for (int j = 0; j < visibilityMap.map.get(0).size(); j++) {
                if (visibilityMap.map.get(i).get(j).getHexVisibility() != HexVisibility.FOG_OF_WAR) {
                    visibilityMap.map.get(i).get(j).setHexVisibility(HexVisibility.DETERMINED);
                }
            }
        }
        adjustVisibilityUnits();
        adjustVisibilityCities();
    }

    private void adjustVisibilityCities() {
        for (City city : cities) {
            for (Hex cityHex : city.getCityHexes()) {
                seeNeighbors(cityHex.getCoordinatesInMap().get('x'), cityHex.getCoordinatesInMap().get('y'));
            }
        }
    }

    private void adjustVisibilityUnits() {

        for (Unit unit : units) {
            int x = unit.getCoordinatesInMap().get('x');
            int y = unit.getCoordinatesInMap().get('y');
            visibilityMap.map.get(x).get(y / 2).setHexVisibility(HexVisibility.TRANSPARENT);
            seeNeighbors(x, y);
            for (NeighborHex neighborHex : NeighborHex.values()) {
                if (!(Game.getGame().map.map.get(x).get(y).getTerrain().name.equals(Terrain.HILL.name) ||
                        Game.getGame().map.map.get(x).get(y).getTerrain().name.equals(Terrain.MOUNTAIN.name) ||
                        Game.getGame().map.map.get(x).get(y).getFeature().name.equals(Feature.JUNGLE.name) ||
                        Game.getGame().map.map.get(x).get(y).getFeature().name.equals(Feature.DENSE_FOREST.name)))
                    seeNeighbors(x + neighborHex.xDiff, y + neighborHex.yDiff);
            }
        }
    }

    private void seeNeighbors(int x, int y) {
        for (NeighborHex neighborHex : NeighborHex.values()) {
            visibilityMap.map.get(x + neighborHex.xDiff).get((y + neighborHex.yDiff) / 2).setHexVisibility(HexVisibility.TRANSPARENT);
        }
    }

    public void setUp() {
        Random random = new Random();
        int xRand;
        int yRand;
        while (true) {
            xRand = random.nextInt(Game.getGame().getRows());
            yRand = random.nextInt(Game.getGame().getColumns());
            if (Game.getGame().map.map.get(xRand).get(yRand).getMovementPrice() >= 0 &
                    Game.getGame().map.map.get(xRand).get(yRand).getMilitaryUnit() == null &
                    Game.getGame().map.map.get(xRand).get(yRand).getCivilUnit() == null)
                break;
        }
        SettlerUnit settlerUnit = new SettlerUnit(xRand, yRand, this, 2, 5, UnitName.SETTLER);
        units.add(settlerUnit);
    }

    public int getScienceStorage() {
        return scienceStorage;
    }

    public void setScienceStorage(int scienceStorage) {
        this.scienceStorage = scienceStorage;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn = sciencePerTurn;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public Map getVisibilityMap() {
        return visibilityMap;
    }

    public void setVisibilityMap(Map visibilityMap) {
        this.visibilityMap = visibilityMap;
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<Technology> technologies) {
        this.technologies = technologies;
    }

    public Technology getTechnologyInProgress() {
        return technologyInProgress;
    }

    public void setTechnologyInProgress(Technology technologyInProgress) {
        this.technologyInProgress = technologyInProgress;
    }

    public ArrayList<UnitName> getOpenedUnits() {
        return openedUnits;
    }

    public void setOpenedUnits(ArrayList<UnitName> openedUnits) {
        this.openedUnits = openedUnits;
    }

    public ArrayList<Resource> getOpenedResources() {
        return openedResources;
    }

    public void setOpenedResources(ArrayList<Resource> openedResources) {
        this.openedResources = openedResources;
    }

    public ArrayList<Feature> getOpenedFeatures() {
        return openedFeatures;
    }

    public void setOpenedFeatures(ArrayList<Feature> openedFeatures) {
        this.openedFeatures = openedFeatures;
    }

    public ArrayList<Improvement> getOpenedImprovements() {
        return openedImprovements;
    }

    public void setOpenedImprovements(ArrayList<Improvement> openedImprovements) {
        this.openedImprovements = openedImprovements;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public ArrayList<Technology> getAvailableTechnologies() {
        return availableTechnologies;
    }

    public void setAvailableTechnologies(ArrayList<Technology> availableTechnologies) {
        this.availableTechnologies = availableTechnologies;
    }
}
