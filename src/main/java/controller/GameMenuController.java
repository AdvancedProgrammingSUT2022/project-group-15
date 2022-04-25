package controller;

import enums.Direction;
import enums.Improvement;
import model.Game;
import model.GlobalThings;
import model.GlobalThings;
import model.Unit;

import java.util.ArrayList;

public class GameMenuController {
    Unit selectedUnit = null;

    public static boolean validCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < Game.getGame().getRows() && y < Game.getGame().getColumns();
    }

    public String changeTurn() {
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            if (unit.needsCommand())
                return "some Units Need Command";
        }
        Game.getGame().nextTurn();
        return "done";
    }

    public String showTechnologyInfo() {
        // TODO : implement
        return null;
    }

    public String showUnitsPanel() {
        // TODO : implement
        return null;
    }

    public String showCitiesPanel() {
        // TODO : implement
        return null;
    }

    public String showDiplomacyPanel() {
        // TODO : implement
        return null;
    }

    public String showVictoryPanel() {
        // TODO : implement
        return null;
    }

    public String showDemographicsPanel() {
        // TODO : implement
        return null;
    }

    public String showNotificationHistory() {
        // TODO : implement
        return null;
    }

    public String showMilitaryPanel() {
        // TODO : implement
        return null;
    }

    public String showEconomyPanel() {
        // TODO : implement
        return null;
    }

    public String showDealsPanel() {
        // TODO : implement
        return null;
    }

    public String selectMilitaryUnit(int x, int y) {
        // TODO : implement
        return null;
    }

    public String selectCivilUnit(int x, int y) {
        // TODO : implement
        return null;
    }

    public String selectCity(String cityName) {
        // TODO : implement
        return null;
    }

    public String selectCity(int x, int y) {
        // TODO : implement
        return null;
    }

    public String moveSelectedUnitTo(int x, int y) {
        if (selectedUnit == null)
            return "no selected unit";
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return "unit not yours";
        }
        int distance = selectedUnit.findShortestPathByDijkstra(x, y);
        if (distance > 999999) {
            return "cant go to destination (mountain or ice or sea ) or blocked by other units";
        }
        selectedUnit.doPlanedMovement();
        if (selectedUnit.getPlanedToGo() == null)
            return "move done";
        return "planed move will be done";
    }

    public String sleepSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String alertSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String fortifySelectedUnit() {
        // TODO : implement
        return null;
    }

    public String fortifySelectedUnitTillHeal() {
        // TODO : implement
        return null;
    }

    public String garrisonSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String setupRangedSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String foundCity() {
        // TODO : implement
        return null;
    }

    public String cancelSelectedUnitMission() {
        // TODO : implement
        return null;
    }

    public String wakeUpSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String deleteSelectedUnit() {
        // TODO : implement
        return null;
    }

    public String attackTo(int x, int y) {
        // TODO : implement
        return null;
    }

    public String buildImprovement(String improvementName) {
        Improvement improvement = Improvement.getImprovementByName(improvementName);
        if (improvement == null) {
            return "invalid improvement";
        }

        // TODO : implement
        return null;
    }

    public String removeJungle() {
        // remove dense-forests requires Bronze-Working Technology
        // remove jungles requires Mining Technology
        // TODO : implement
        return null;
    }

    public String removeRoute() {
        // TODO : implement
        return null;
    }

    public String repair() {
        // TODO : implement
        return null;
    }

    public String showMap() {
        String[][] str = new String[GlobalThings.height][GlobalThings.width];
        for (int i = 0; i < GlobalThings.height; i++) {
            for (int j = 0; j < GlobalThings.width; j++) {
                str[i][j] = GlobalThings.black + '█';
            }
        }
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 6; j++) {
                int x = (i + 1) * GlobalThings.arz / 2;
                int y = j * GlobalThings.tool * 2 + GlobalThings.tool;
                if (i % 2 == 1) y += GlobalThings.tool;
                for (int k = 0; k < GlobalThings.arz / 2; k++) {
                    for (int l = 0; l <= 9 - k; l++) {
                        for (int z = y - l; z <= y + l; z++) {
                            if (z == y - l) str[x - k][z - 1] = "/";
                            if (z == y + l) str[x - k][z + 1] = "\\";
                            str[x - k][z] = GlobalThings.red + '█';
                            str[x + k][z] = GlobalThings.red + '█';
                        }
                    }
                }
                str[x + 1][y - 2] = "RI";
                str[x + 1][y - 1] = "";
                str[x + 1][y] = ":";
                if (Game.getGame().map.get(i / 2).get(2 * j + i % 2).doesHaveRiver())
                    str[x + 1][y + 1] = "ys";
                else
                    str[x + 1][y + 1] = "no";
                str[x + 1][y + 2] = "";

                str[x][y - 2] = GlobalThings.blue + "T";
                str[x][y - 1] = "R";
                str[x][y] = ":";
                str[x][y + 1] = Game.getGame().map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(0, 1);
                str[x][y + 2] = Game.getGame().map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(1, 2);

                str[x - 1][y - 2] = "FE";
                str[x - 1][y - 1] = "";
                str[x - 1][y] = ":";
                str[x - 1][y + 1] = Game.getGame().map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(0, 1);
                str[x - 1][y + 2] = Game.getGame().map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(1, 2);



                str[x + 4][y] = "-";
                str[x + 4][y + 1] = "-";
                str[x + 4][y + 2] = "-";
                str[x + 4][y + 3] = "-";
                str[x + 4][y + 4] = "-";
                str[x + 4][y + 5] = "-";
                str[x - 4][y] = "-";
                str[x - 4][y + 1] = "-";
                str[x - 4][y + 2] = "-";
                str[x - 4][y + 3] = "-";
                str[x - 4][y + 4] = "-";
                str[x - 4][y + 5] = "-";
                str[x + 4][y - 1] = "-";
                str[x + 4][y - 2] = "-";
                str[x + 4][y - 3] = "-";
                str[x + 4][y - 4] = "-";
                str[x + 4][y - 5] = "-";
                str[x - 4][y - 1] = "-";
                str[x - 4][y - 2] = "-";
                str[x - 4][y - 3] = "-";
                str[x - 4][y - 4] = "-";
                str[x - 4][y - 5] = "-";
            }
        }
        for (int i = 0; i < GlobalThings.height; i++) {
            for (int j = 0; j < GlobalThings.width; j++) {
                System.out.print(str[i][j]);
            }
            System.out.println("");
        }
        return "print map successfully";
    }


    public String moveMap(String directionName, int amount) {
        Direction direction = Direction.getDirectionByName(directionName);
        if (direction == null) {
            return "invalid direction";
        }

        // TODO : implement
        return null;
    }

    // TODO : implement removing Swamp ( that requires Masonry Technology )
}
