package server.model.unit;

import server.enums.UnitName;
import server.model.City;
import server.model.Civilization;
import server.model.Game;

public class SettlerUnit extends CivilUnit {
    public SettlerUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public boolean needsCommand() {
        if (isSleep)
            return false;
        if (PlanedToGo != null){
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
                this.coordinatesInMap.get('x')/2,this.coordinatesInMap.get('y'),owner);
        if (this.owner.getCities().size()==0) {
            this.owner.setCapital(city);
            Game.getGame().getOriginalCapitals().add(city);
        }
        this.owner.getCities().add(city);
        owner.deleteUnit(this, false);
    }

}
