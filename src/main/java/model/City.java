package model;

import enums.NeighborHex;
import enums.UnitName;
import model.unit.RangedMilitary;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class City {
    private String name;
    private Civilization owner;
    private int neededProduction;
    private int remainedTurns;
    private UnitName progressUnit;
    private Unit unitInProgress;
    private int numberOfCitizen;
    private HashMap<Character, Integer> coordinatesOfCenterInArray = new HashMap<>();
    private int foodStorage;
    private int foodPerTurn;
    private int productionPerTurn;
    private int goldPerTurn;
    private int sciencePerTurn;
    private final ArrayList<Hex> cityHexes = new ArrayList<>();
    private RangedMilitary cityUnit;
    private int unemployedCitizens = 0;

    public City(String name, int x, int y) {
        this.name = name;
        coordinatesOfCenterInArray.put('x', x);
        coordinatesOfCenterInArray.put('y', y);
        this.cityHexes.add(Game.getGame().map.map.get(x).get(y));

        for (NeighborHex neighborHex : NeighborHex.values()) {
            this.cityHexes.add(Game.getGame().map.map.get((2 * x + y % 2 + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff));
        }

        Game.getGame().map.map.get(x).get(y).setCity(this);
    }

    public int getUnemployedCitizens() {
        return unemployedCitizens;
    }

    public void setUnemployedCitizens(int unemployedCitizens) {
        this.unemployedCitizens = unemployedCitizens;
    }

    public UnitName getProgressUnit() {
        return progressUnit;
    }

    public void setProgressUnit(UnitName progressUnit) {
        this.progressUnit = progressUnit;
    }

    public ArrayList<Hex> getCityHexes() {
        return cityHexes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainedTurns() {
        return remainedTurns;
    }

    public void setRemainedTurns(int remainedTurns) {
        this.remainedTurns = remainedTurns;
    }

    public Civilization getOwner() {
        return owner;
    }

    public void setOwner(Civilization owner) {
        this.owner = owner;
    }

    public int getNeededProduction() {
        return neededProduction;
    }

    public void setNeededProduction(int neededProduction) {
        this.neededProduction = neededProduction;
    }

    public Unit getUnitInProgress() {
        return unitInProgress;
    }

    public void setUnitInProgress(Unit unitInProgress) {
        this.unitInProgress = unitInProgress;
    }

    public int getNumberOfCitizen() {
        return numberOfCitizen;
    }

    public void setNumberOfCitizen(int numberOfCitizen) {
        this.numberOfCitizen = numberOfCitizen;
    }

    public HashMap<Character, Integer> getCoordinatesOfCenterInArray() {
        return coordinatesOfCenterInArray;
    }

    public void setCoordinatesOfCenterInArray(HashMap<Character, Integer> coordinatesOfCenterInArray) {
        this.coordinatesOfCenterInArray = coordinatesOfCenterInArray;
    }

    public int getFoodStorage() {
        return foodStorage;
    }

    public void setFoodStorage(int foodStorage) {
        this.foodStorage = foodStorage;
    }

    public int getFoodPerTurn() {
        return foodPerTurn;
    }

    public void setFoodPerTurn(int foodPerTurn) {
        this.foodPerTurn = foodPerTurn;
    }

    public int getProductionPerTurn() {
        return productionPerTurn;
    }

    public void setProductionPerTurn(int productionPerTurn) {
        this.productionPerTurn = productionPerTurn;
    }

    public int getGoldPerTurn() {
        return goldPerTurn;
    }

    public void setGoldPerTurn(int goldPerTurn) {
        this.goldPerTurn = goldPerTurn;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn = sciencePerTurn;
    }

    public RangedMilitary getCityUnit() {
        return cityUnit;
    }

    public void setCityUnit(RangedMilitary cityUnit) {
        this.cityUnit = cityUnit;
    }

    public int calculateGoldPerTurn() {
        int ans = 0;
        for (Hex hex : cityHexes) {
            if (hex.isAnyCitizenWorking()) {
                ans += hex.getTerrain().gold + hex.getFeature().gold + hex.getImprovement().gold;
            }
            if (hex.getResource().requiredImprovement.equals(hex.getImprovement())) {
                ans += hex.getResource().gold;
            }
        }
        return ans;
    }

    public int calculateProductionPerTurn() {
        int ans = 0;
        int copyOfNumberOfCitizen = numberOfCitizen;
        for (Hex hex : cityHexes) {
            if (hex.isAnyCitizenWorking()) {
                ans += hex.getTerrain().production + hex.getFeature().production + hex.getImprovement().production;
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement())) {
                    ans += hex.getResource().production;
                }
                copyOfNumberOfCitizen--;
            }

        }
        return ans + copyOfNumberOfCitizen;
    }

    public int calculateFoodPerTurn() {
        int ans = 0;
        for (Hex hex : cityHexes) {
            if (hex.isAnyCitizenWorking()) {
                ans += hex.getTerrain().food + hex.getFeature().food + hex.getImprovement().food;
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement())) {
                    ans += hex.getResource().food;
                }
            }
        }
        ans -= 2 * numberOfCitizen;
        if (Civilization.getHappiness() < 0 && ans > 0) {
            ans = (int) 2 * ans / 3;
            return ans;
        } else if (unitInProgress.getName().equals("Settler") && ans > 0) {
            return 0;
        }
        return ans;
    }

    public void moveToNextTurn() {
        productionPerTurn = calculateProductionPerTurn();
        goldPerTurn = calculateGoldPerTurn();
        foodPerTurn = calculateFoodPerTurn();
        sciencePerTurn = numberOfCitizen;


        foodStorage += foodPerTurn;
        neededProduction -= productionPerTurn;
        // TODO: 5/9/2022 production done? new citizen or starve

    }

    public void lockCitizenToHex(int x, int y) {

        Game.getGame().map.map.get(x).get(y).setAnyCitizenWorking(true);
        unemployedCitizens++;

    }

    public void removeCitizenFromHex(int x, int y) {

        Game.getGame().map.map.get(x).get(y).setAnyCitizenWorking(false);
        unemployedCitizens--;

    }

}
