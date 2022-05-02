package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.Unit;

public abstract class MilitaryUnit extends Unit {

    protected boolean isAlerted;
    protected boolean isFortifying;
    protected boolean isFortifyingTillHealed;

    public MilitaryUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    abstract public void attackTo(Unit unit);

    public void garrisonCity() {
// TODO: 4/23/2022
    }

    public boolean enemyIsNear() {
        // TODO: 4/23/2022
        return true;
    }

    public boolean needsCommand() {
        if (this.remainingMovement < 0)
            return false;
        if (PlanedToGo != null) {
            doPlanedMovement();
            if (remainingMovement > 0)
                return true;
            return false;
        }
        return true;
    }

    public void cancelMission() {
        PlanedToGo = null;
    }

}
