package model;

import enums.UnitName;

public class SettlerUnit extends CivilUnit {
    public SettlerUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    public void foundCity() {

    }

}
