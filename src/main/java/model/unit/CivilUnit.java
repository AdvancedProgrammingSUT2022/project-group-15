package model.unit;

import enums.UnitName;
import model.Civilization;
import model.unit.Unit;

public abstract class CivilUnit extends Unit {
    public CivilUnit(int x, int y, Civilization owner,  UnitName name) {
        super(x, y, owner, name);
    }

}
