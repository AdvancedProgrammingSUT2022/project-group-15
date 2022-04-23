package model;

import enums.UnitName;

public class MeleeMilitary extends MilitaryUnit{
    public MeleeMilitary(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    @Override
    public void attackTo(Unit unit) {// priority with military unit
        if (unit instanceof CivilUnit){
            moveToHex(unit.coordinates.get('x'),unit.coordinates.get('y'));
            unit.deleteUnit(false);
            // TODO: 4/23/2022 build worker here for attacker
            return;
        }
        unit.nowHealth -= this.meleePower;
        this.nowHealth -= unit.meleePower;

        if (unit.nowHealth<=0) {
            moveToHex(unit.coordinates.get('x'),unit.coordinates.get('y'));
            unit.deleteUnit(false);
        }

        if (this.nowHealth <=0 ){
            this.deleteUnit(false);
        }

    }

}
