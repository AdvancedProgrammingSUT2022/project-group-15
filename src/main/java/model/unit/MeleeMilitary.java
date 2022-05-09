package model.unit;

import enums.UnitName;
import model.Civilization;

public class MeleeMilitary extends MilitaryUnit {
    public MeleeMilitary(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public void attackTo(Unit unit) {// priority with military unit
        if (unit instanceof CivilUnit){
            moveToHex(unit.coordinatesInMap.get('x'),unit.coordinatesInMap.get('y'));
            unit.owner.deleteUnit(this, false);
            // TODO: 4/23/2022 build worker here for attacker
            return;
        }
        unit.nowHealth -= this.meleePower;
        this.nowHealth -= unit.meleePower;

        if (unit.nowHealth<=0) {
            moveToHex(unit.coordinatesInMap.get('x'),unit.coordinatesInMap.get('y'));
            unit.owner.deleteUnit(this, false);
        }

        if (this.nowHealth <=0 ){
            unit.owner.deleteUnit(this, false);
        }

    }

}
