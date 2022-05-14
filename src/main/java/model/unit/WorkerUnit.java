package model.unit;

import enums.Feature;
import enums.Improvement;
import enums.UnitName;
import model.Civilization;
import model.Game;
import model.Hex;
import model.unit.CivilUnit;

import java.util.IdentityHashMap;

import static java.lang.Math.min;

public class WorkerUnit extends CivilUnit {
    private boolean isWorking;
    private boolean isRemoving;
    private boolean isBuildingRoad;
    private int turnTillRemove;
    private int turnTillBuildRoad;

    public WorkerUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public boolean needsCommand() {
        if (isWorking) {
            Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y'));
            hex.setPercentOfBuildingImprovement(min(hex.getPercentOfBuildingImprovement() + 10, 100));
            if (hex.getPercentOfBuildingImprovement() == 100) {
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement()) && hex.getResource().type.equals("strategic"))
                    this.owner.getStrategicResources().add(hex.getResource());
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement()) && hex.getResource().type.equals("luxury"))
                    this.owner.getLuxuryResources().add(hex.getResource());
                isWorking = false;
            }
            return false;
        }
        if (isRemoving) {
            turnTillRemove--;
            if (turnTillRemove <= 0) {
                Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y'));
                hex.setFeature(Feature.NULL);
                isRemoving = false;
            }
            return false;

        }
        if (isBuildingRoad) {
            turnTillBuildRoad--;
            if (turnTillBuildRoad <= 0) {
                Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y'));
                isBuildingRoad = false;
                if (hex.isHasRailRoad()) {
                    return false;
                }
                if (hex.isHasRoad())
                    hex.setHasRailRoad(true);
                else
                    hex.setHasRoad(true);
                this.owner.setBuildingMaintenance(this.owner.getBuildingMaintenance() + 1);
            }
            return false;
        }
        if (PlanedToGo != null) {
            return false;
        }
        return true;
    }

    public void buildImprovement(Improvement improvement) {
        isWorking = true;
        isRemoving = false;
        Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x') / 2).get(this.coordinatesInMap.get('y'));
        hex.setPercentOfBuildingImprovement(0);
        hex.setImprovement(improvement);
    }

    public void buildRoad(Improvement improvement) {
        isWorking = false;
        isRemoving = false;
        isBuildingRoad = true;
        turnTillBuildRoad = 3;

    }

    public void removeJungle() {
        isRemoving = true;
        isWorking = false;
        isBuildingRoad = false;
        turnTillRemove = 5;
    }

    public void cancelMission() {
        isWorking = false;
        isRemoving = false;
        isBuildingRoad =false;
        PlanedToGo = null;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setRemoving(boolean removing) {
        isRemoving = removing;
    }

    public void setBuildingRoad(boolean buildingRoad) {
        isBuildingRoad = buildingRoad;
    }
}
