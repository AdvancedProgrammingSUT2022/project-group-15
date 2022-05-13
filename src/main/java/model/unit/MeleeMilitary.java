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
        this.remainingMovement = -1;
        if (unit instanceof CivilUnit) {
            moveToHex(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'));
            unit.owner.deleteUnit(unit, false);
            new WorkerUnit(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'), this.owner, UnitName.WORKER);
            return;
        }
        unit.loseHealth(this.meleePower,this);
        this.loseHealth(unit.meleePower,unit);


        if (unit.nowHealth <= 0) {
            moveToHex(unit.coordinatesInMap.get('x') / 2, unit.coordinatesInMap.get('y'));
            unit.owner.deleteUnit(unit, false);
            if (unit.getName().equals(UnitName.CITYUNIT)) {
                SettlerUnit settlerUnit = new SettlerUnit(unit.getCoordinatesInMap().get('x') / 2,
                        unit.getCoordinatesInMap().get('y'), this.owner, UnitName.SETTLER);
                settlerUnit.foundCity();
            }

        }

        if (this.nowHealth <= 0) {
            this.owner.deleteUnit(this, false);
        }

    }

}
