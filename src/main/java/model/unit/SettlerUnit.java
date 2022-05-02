package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.CivilUnit;

public class SettlerUnit extends CivilUnit {
    public SettlerUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    @Override
    public boolean needsCommand() {
        if (PlanedToGo != null){
            doPlanedMovement();
            if (remainingMovement>0)
                return true;
            return false;
        }
        return true;
    }

    @Override
    public void cancelMission() {
        PlanedToGo = null;
    }

    public void foundCity() {
        // TODO: 4/23/2022
    }

}
