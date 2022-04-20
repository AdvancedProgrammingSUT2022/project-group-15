package model;

import enums.UnitName;

public class MeleeMilitary extends MilitaryUnit{
    public MeleeMilitary(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }
}
