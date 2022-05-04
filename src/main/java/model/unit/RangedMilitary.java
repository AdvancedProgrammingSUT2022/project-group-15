package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.MilitaryUnit;
import model.unit.Unit;

public class RangedMilitary extends MilitaryUnit {
    private int rangedPower;
    private int range;
    private boolean isSetup;

    public RangedMilitary(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    @Override
    public void attackTo(Unit unit){
        unit.nowHealth -= this.rangedPower;
        if (unit.nowHealth<=0) {
            unit.owner.deleteUnit(this, false);
        }
    }

}
