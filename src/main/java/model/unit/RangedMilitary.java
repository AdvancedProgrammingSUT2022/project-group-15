package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.MilitaryUnit;
import model.unit.Unit;

public class RangedMilitary extends MilitaryUnit {
    private int rangedPower;
    private int range;
    private boolean isSetup;

    public RangedMilitary(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
        this.rangedPower=name.getRangedCombatStrength();
        this.range = name.getRange();
        this.isSetup=false;
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
    public void attackTo(Unit unit){
        this.remainingMovement = -1;
        if (unit instanceof CivilUnit){
            unit.owner.deleteUnit(unit, false);
            return;
        }
        unit.nowHealth -= this.rangedPower;
        if (unit.nowHealth<=0) {
            unit.owner.deleteUnit(unit, false);
        }
    }

}
