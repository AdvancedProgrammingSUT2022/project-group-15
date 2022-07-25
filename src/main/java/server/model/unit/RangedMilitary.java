package server.model.unit;

import server.enums.UnitName;
import server.model.Civilization;

public class RangedMilitary extends MilitaryUnit {
    private int rangedPower;
    private int range;
    private boolean isSetup;

    public RangedMilitary(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
        this.rangedPower = name.getRangedCombatStrength();
        this.range = name.getRange();
        this.isSetup = false;
    }


    public int getRangedPower() {
        return rangedPower;
    }

    public void setRangedPower(int rangedPower) {
        this.rangedPower = rangedPower;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
    }

    @Override
    public void attackTo(Unit unit) {
        this.remainingMovement = -1;
        if (unit instanceof CivilUnit) {
            unit.owner.deleteUnit(unit, false);
            return;
        }
        unit.loseHealth(this.rangedPower, this);
        if (unit.nowHealth <= 0) {

            if (unit.getName().equals(UnitName.CITYUNIT)) {
                unit.setNowHealth(1);
            }
            else
                unit.owner.deleteUnit(unit, false);
        }
    }

}
