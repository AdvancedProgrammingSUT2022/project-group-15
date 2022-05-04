package model.unit;

import enums.UnitName;
import model.City;
import model.Civilization;
import model.unit.CivilUnit;

public class SettlerUnit extends CivilUnit {
    public SettlerUnit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        super(x, y, owner, movementSpeed, health, name);
    }

    @Override
    public boolean needsCommand() {
        if (PlanedToGo != null){
            doPlanedMovement();
            if (remainingMovement>0)
                return true;
            return false;
        }
        return true;
    }

    @Override
    public void cancelMission() {
        PlanedToGo = null;
    }

    public void foundCity() {
        City city = new City(owner.getUser().getNickname()+Integer.toString(this.owner.getCities().size()+1),
                this.coordinatesInMap.get('x'),this.coordinatesInMap.get('y')/2);
        this.owner.getCities().add(city);
        owner.deleteUnit(this, false);
    }

}
