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

    @Override
    public void attackTo(Unit unit){
        unit.nowHealth -= this.rangedPower;
        if (unit.nowHealth<=0) {
            unit.owner.deleteUnit(this, false);
        }
    }

}
