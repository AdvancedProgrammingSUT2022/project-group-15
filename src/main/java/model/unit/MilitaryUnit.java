package model.unit;


import enums.Terrain;
import enums.UnitName;
import model.City;
import model.Civilization;
import model.Game;
import model.Hex;


public abstract class MilitaryUnit extends Unit {

    protected boolean isAlerted;
    protected boolean isFortifying;
    protected boolean isFortifyingTillHealed;
    protected boolean isGarrisoning;

    public MilitaryUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    public boolean isGarrisoning() {
        return isGarrisoning;
    }

    public void setGarrisoning(boolean garrisoning) {
        isGarrisoning = garrisoning;
    }

    abstract public void attackTo(Unit unit);



    public void garrisonCity() {
        City city = Game.getGame().map.map.get(this.getCoordinatesInMap().get('x')/2).get(this.getCoordinatesInMap().get('y')).getCity();
        city.garrison();
    }
    public void unGarrisonCity() {
        City city = Game.getGame().map.map.get(this.getCoordinatesInMap().get('x')/2).get(this.getCoordinatesInMap().get('y')).getCity();
        city.unGarrison();
    }


    public boolean needsCommand() {
        if (isSleep)
            return false;
        if (this.remainingMovement < 0)
            return false;
        if (isGarrisoning || isFortifyingTillHealed || isAlerted)
            return false;

        if (PlanedToGo != null) {
            return false;
        }
        return true;
    }

    public void cancelMission() {
        PlanedToGo = null;
    }

    public boolean isAlerted() {
        return isAlerted;
    }

    public void setAlerted(boolean alerted) {
        isAlerted = alerted;
    }

    public boolean isFortifying() {
        return isFortifying;
    }

    public void setFortifying(boolean fortifying) {
        isFortifying = fortifying;
    }

    public boolean isFortifyingTillHealed() {
        return isFortifyingTillHealed;
    }

    public void setFortifyingTillHealed(boolean fortifyingTillHealed) {
        isFortifyingTillHealed = fortifyingTillHealed;
    }
}
