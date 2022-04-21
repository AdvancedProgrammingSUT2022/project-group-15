package model;

import enums.Improvement;
import enums.UnitName;

public class WorkerUnit extends CivilUnit {
    private boolean isWorking;

    public WorkerUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    public void buildImprovement(Improvement improvement) {

    }

    public void removeJungle() {

    }

    public void cancelMission() {

    }

    public void removeRoute() {
    }

}
