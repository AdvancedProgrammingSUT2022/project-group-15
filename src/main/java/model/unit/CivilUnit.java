package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.Unit;

public abstract class CivilUnit extends Unit {
    public CivilUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

}
