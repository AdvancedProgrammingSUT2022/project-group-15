package model;

import enums.UnitName;

public class CivilUnit extends Unit {
    public CivilUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    public boolean needsCommand() {
        return true;
    }

}
