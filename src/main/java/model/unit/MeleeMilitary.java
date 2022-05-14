package model.unit;

import enums.UnitName;
import model.Civilization;
import model.Game;

public class MeleeMilitary extends MilitaryUnit {
    public MeleeMilitary(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public void attackTo(Unit unit) {// priority with military unit
        if (this.name.getCombatType().equals("Mounted") || this.name.getCombatType().equals("Armored"))
            this.remainingMovement -= 1;
        else
            this.remainingMovement = -1;

        if (unit instanceof CivilUnit) {
            moveToHex(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'));
            unit.owner.deleteUnit(unit, false);
            new WorkerUnit(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'), this.owner, UnitName.WORKER);
            return;
        }
        unit.loseHealth(this.meleePower, this);
        this.loseHealth(unit.meleePower, unit);


        if (unit.nowHealth <= 0) {
            unit.owner.deleteUnit(unit, false);
            moveToHex(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'));
        }

        if (this.nowHealth <= 0) {
            this.owner.deleteUnit(this, false);
        }

    }

}
