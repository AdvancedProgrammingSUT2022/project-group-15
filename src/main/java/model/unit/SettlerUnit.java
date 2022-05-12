package model.unit;

import enums.UnitName;
import model.City;
import model.Civilization;
import model.unit.CivilUnit;

public class SettlerUnit extends CivilUnit {
    public SettlerUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public boolean needsCommand() {
        if (PlanedToGo != null){
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
        City city = new City(Integer.toString(this.owner.getCities().size()+1)+owner.getUser().getNickname(),
                this.coordinatesInMap.get('x')/2,this.coordinatesInMap.get('y'));
        this.owner.getCities().add(city);
        owner.deleteUnit(this, false);
    }

}
