package model.unit;

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

    public WorkerUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public boolean needsCommand() {
        if (isWorking) {
            Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x')/2).get(this.coordinatesInMap.get('y'));
            hex.setPercentOfBuildingImprovement(min(hex.getPercentOfBuildingImprovement()+10,100));
            if (hex.getPercentOfBuildingImprovement()==100) {
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement()) && hex.getResource().type.equals("strategic"))
                    this.owner.getStrategicResources().add(hex.getResource());
                if (hex.getResource().requiredImprovement.equals(hex.getImprovement()) && hex.getResource().type.equals("luxury"))
                    this.owner.getLuxuryResources().add(hex.getResource());
                isWorking = false;
            }
            return false;
        }
        if (PlanedToGo != null){
            if (remainingMovement>0)
                return true;
            return false;
        }
        return true;
    }

    public void buildImprovement(Improvement improvement) {
        isWorking = true;
        Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x')/2).get(this.coordinatesInMap.get('y'));
        hex.setPercentOfBuildingImprovement(0);
        hex.setImprovement(improvement);
    }

    public void buildRoad(Improvement improvement) {
        isWorking = true;
        Hex hex = Game.getGame().map.map.get(this.coordinatesInMap.get('x')/2).get(this.coordinatesInMap.get('y'));
        if (improvement.equals(Improvement.ROAD)) {
            if (hex.isHasRoad())
                return;
            hex.setHasRoad(true);
        }
        else {
            if (hex.isHasRailRoad())
                return;
            hex.setHasRoad(true);
            hex.setHasRailRoad(true);
        }
        this.owner.setBuildingMaintenance(this.owner.getBuildingMaintenance()+1);
    }
    public void removeJungle() {
        // TODO: 4/23/2022
    }

    public void cancelMission() {
        isWorking = false;
        PlanedToGo = null;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
