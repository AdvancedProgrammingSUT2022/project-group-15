package server.model.unit;

import server.enums.UnitName;
import server.model.Civilization;

public abstract class CivilUnit extends Unit {
    public CivilUnit(int x, int y, Civilization owner,  UnitName name) {
        super(x, y, owner, name);
    }

}
