package model.unit;

import enums.Improvement;
import enums.UnitName;
import model.Civilization;
import model.unit.CivilUnit;

public class WorkerUnit extends CivilUnit {
    private boolean isWorking;

    public WorkerUnit(int x, int y, Civilization owner, UnitName name) {
        super(x, y, owner, name);
    }

    @Override
    public boolean needsCommand() {
        if (isWorking) {
            // TODO: 4/23/2022
            return false;
        }
        if (PlanedToGo != null){
            doPlanedMovement();
            if (remainingMovement>0)
                return true;
            return false;
        }
        return true;
    }

    public void buildImprovement(Improvement improvement) {
        // TODO: 4/23/2022
    }

    public void removeJungle() {
        // TODO: 4/23/2022
    }

    public void cancelMission() {
        isWorking = false;
        PlanedToGo = null;
    }

    public void removeRoute() {
        // TODO: 4/23/2022
    }

}
