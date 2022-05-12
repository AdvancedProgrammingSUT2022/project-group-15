package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.Unit;

public abstract class MilitaryUnit extends Unit {

    protected boolean isAlerted;
    protected boolean isFortifying;
    protected boolean isFortifyingTillHealed;

    public MilitaryUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
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

    public boolean isAlerted() {
        return isAlerted;
    }

    public void setAlerted(boolean alerted) {
        isAlerted = alerted;
    }

    public boolean isFortifying() {
        return isFortifying;
    }

    public void setFortifying(boolean fortifying) {
        isFortifying = fortifying;
    }

    public boolean isFortifyingTillHealed() {
        return isFortifyingTillHealed;
    }

    public void setFortifyingTillHealed(boolean fortifyingTillHealed) {
        isFortifyingTillHealed = fortifyingTillHealed;
    }
}
