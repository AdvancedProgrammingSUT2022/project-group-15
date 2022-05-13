package model;

import enums.NeighborHex;
import enums.UnitName;
import model.unit.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.*;

public class City {
    private String name;
    private Civilization owner;
    private int neededProduction;
    private int remainedTurns;
    private UnitName unitInProgress;
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

    public City(String name, int x, int y, Civilization owner) {
        this.name = name;
        this.owner = owner;
        unitInProgress = UnitName.NULL;
        neededProduction = 999999;
        coordinatesOfCenterInArray.put('x', x);
        coordinatesOfCenterInArray.put('y', y);
        numberOfCitizen = 1;

        Game.getGame().map.map.get(x).get(y).setHasRoad(true);
        Game.getGame().map.map.get(x).get(y).setAnyCitizenWorking(true);
        this.cityHexes.add(Game.getGame().map.map.get(x).get(y));


        for (NeighborHex neighborHex : NeighborHex.values()) {
            if (Game.getGame().map.validCoordinateInArray((2 * x + y % 2 + neighborHex.xDiff) / 2, y + neighborHex.yDiff))
                this.cityHexes.add(Game.getGame().map.map.get((2 * x + y % 2 + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff));
        }

        Game.getGame().map.map.get(x).get(y).setCity(this);
        cityUnit = new RangedMilitary(x, y, owner, UnitName.CITYUNIT);
    }

    public int getUnemployedCitizens() {
        return unemployedCitizens;
    }

    public void setUnemployedCitizens(int unemployedCitizens) {
        this.unemployedCitizens = unemployedCitizens;
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

    public UnitName getUnitInProgress() {
        return unitInProgress;
    }

    public void setUnitInProgress(UnitName unitInProgress) {
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
                ans += hex.getTerrain().gold + hex.getFeature().gold;
            }
            if (!hex.isHasDestroyedImprovement() && hex.getPercentOfBuildingImprovement() == 100)
                ans += hex.getImprovement().gold;

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
                ans += hex.getTerrain().production + hex.getFeature().production;
                if (!hex.isHasDestroyedImprovement() && hex.getPercentOfBuildingImprovement() == 100)
                    ans += hex.getImprovement().production;
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
                ans += hex.getTerrain().food + hex.getFeature().food;
                if (!hex.isHasDestroyedImprovement() && hex.getPercentOfBuildingImprovement() == 100)
                    ans += hex.getImprovement().food;
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement())) {
                    ans += hex.getResource().food;
                }
            }
        }
        ans -= 2 * numberOfCitizen;
        if (owner.getHappiness() < 0 && ans > 0) {
            ans = 2 * ans / 3;
            return ans;
        } else if (unitInProgress.equals(UnitName.SETTLER) && ans > 0) {
            return 0;
        }
        return ans;
    }

    public void moveToNextTurn() {
        productionPerTurn = calculateProductionPerTurn();
        goldPerTurn = calculateGoldPerTurn();
        foodPerTurn = calculateFoodPerTurn();
        sciencePerTurn = numberOfCitizen;
        if (productionPerTurn < 0 || neededProduction > 99999)
            remainedTurns = 9999999;
        else
            remainedTurns = neededProduction / productionPerTurn;


        foodStorage += foodPerTurn;
        neededProduction -= productionPerTurn;


        if (foodStorage > pow(2, numberOfCitizen)) {
            foodStorage = 0;
            numberOfCitizen++;
        }
        if (foodStorage < 0) {
            if (numberOfCitizen == 1)
                foodStorage = 0;
            else {
                numberOfCitizen--;
                foodStorage = (int) pow(2, numberOfCitizen);
            }
        }
        if (unitInProgress != UnitName.NULL) {
            if (neededProduction <= 0) {
                createUnitInCity(unitInProgress);
                unitInProgress = UnitName.NULL;
                neededProduction = 9999999;
            }
        }
        this.cityUnit.setNowHealth(min(this.cityUnit.getNowHealth() + this.cityUnit.getTotalHealth() / 10, this.cityUnit.getTotalHealth()));
    }

    public void createUnitInCity(UnitName unitName) {
        if (unitName.getCombatType().equals("Civilian")) {
            if (Game.getGame().map.map.get(coordinatesOfCenterInArray.get('x')).get(coordinatesOfCenterInArray.get('y'))
                    .getCivilUnit() == null) {
                if (unitName.equals(UnitName.SETTLER)) {
                    new SettlerUnit(coordinatesOfCenterInArray.get('x'), coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                } else {
                    new WorkerUnit(coordinatesOfCenterInArray.get('x'), coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                }
            } else {
                if (unitName.equals(UnitName.SETTLER)) {
                    new SettlerUnit(coordinatesOfCenterInArray.get('x') + 1, coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                } else {
                    new WorkerUnit(coordinatesOfCenterInArray.get('x') + 1, coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                }
            }
        } else {
            if (Game.getGame().map.map.get(coordinatesOfCenterInArray.get('x')).get(coordinatesOfCenterInArray.get('y'))
                    .getMilitaryUnit() == null) {
                if (unitName.getRangedCombatStrength() == 0) {
                    new MeleeMilitary(coordinatesOfCenterInArray.get('x'), coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                } else {
                    new RangedMilitary(coordinatesOfCenterInArray.get('x'), coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                }
            } else {
                if (unitName.getRangedCombatStrength() == 0) {
                    new MeleeMilitary(coordinatesOfCenterInArray.get('x') + 1, coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                } else {
                    new RangedMilitary(coordinatesOfCenterInArray.get('x') + 1, coordinatesOfCenterInArray.get('y'), this.owner, unitName);
                }
            }
        }
    }

    public void lockCitizenToHex(int x, int y) {

        Game.getGame().map.map.get(x).get(y).setAnyCitizenWorking(true);
        unemployedCitizens++;

    }

    public void removeCitizenFromHex(int x, int y) {

        Game.getGame().map.map.get(x).get(y).setAnyCitizenWorking(false);
        unemployedCitizens--;
    }

    public void buyHex(int x, int y) {
        this.owner.setGoldStorage(this.owner.getGoldStorage() - 30);
        cityHexes.add(Game.getGame().map.map.get(x).get(y));
    }

    public void garrison() {
        cityUnit.setRangedPower((int) (cityUnit.getRangedPower() * 1.5));
        cityUnit.setMeleePower((int) (cityUnit.getMeleePower() * 1.5));
        cityUnit.setTotalHealth((int) (cityUnit.getTotalHealth() * 1.5));
    }

    public void unGarrison() {
        cityUnit.setRangedPower((cityUnit.getRangedPower() * 2 / 3));
        cityUnit.setMeleePower((cityUnit.getMeleePower() * 2 / 3));
        cityUnit.setTotalHealth((cityUnit.getTotalHealth() * 2 / 3));
    }

}
