package controller;

import enums.Direction;
import enums.Improvement;
import model.Game;
import model.GlobalThings;
import model.unit.Unit;

public class GameMenuController {
    Unit selectedUnit = null;



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
        String[][] printMap = new String[GlobalThings.mapHeight][GlobalThings.mapWidth];
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                printMap[i][j] = GlobalThings.BLACK + '█';
            }
        }
        for (int i = 0; i < Game.getGame().getRows()*2; i++) {
            for (int j = 0; j < Game.getGame().getColumns()/2; j++) {
                int x = (i + 1) * GlobalThings.widthOfGrid / 2;
                int y = j * GlobalThings.lengthOfGrid * 2 + GlobalThings.lengthOfGrid;
                if (i % 2 == 1) y += GlobalThings.lengthOfGrid;
                for (int k = 0; k < GlobalThings.widthOfGrid / 2; k++) {
                    for (int l = 0; l <= 9 - k; l++) {
                        for (int z = y - l; z <= y + l; z++) {
                            if (z == y - l) printMap[x - k][z - 1] = "/";
                            if (z == y + l) printMap[x - k][z + 1] = "\\";
                            printMap[x - k][z] = GlobalThings.GREEN + '█';
                            printMap[x + k][z] = GlobalThings.GREEN + '█';
                        }
                    }
                }
                printMap[x + 1][y - 2] = GlobalThings.BLUE+ "RI";
                printMap[x + 1][y - 1] = "";
                printMap[x + 1][y] = ":";
                if (Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).doesHaveRiver())
                    printMap[x + 1][y + 1] = "ys";
                else
                    printMap[x + 1][y + 1] = "no";
                printMap[x + 1][y + 2] = "";

                printMap[x][y - 2] = GlobalThings.YELLOW +"T";
                printMap[x][y - 1] = "R";
                printMap[x][y] = ":";
                printMap[x][y + 1] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(0, 1);
                printMap[x][y + 2] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getTerrain().name.substring(1, 2);

                printMap[x - 1][y - 2] = GlobalThings.RED + "FE";
                printMap[x - 1][y - 1] = "";
                printMap[x - 1][y] = ":";
                printMap[x - 1][y + 1] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(0, 1);
                printMap[x - 1][y + 2] = Game.getGame().map.map.get(i / 2).get(2 * j + i % 2).getFeature().name.substring(1, 2);


                printMap[x + 4][y] = "-";
                printMap[x + 4][y + 1] = "-";
                printMap[x + 4][y + 2] = "-";
                printMap[x + 4][y + 3] = "-";
                printMap[x + 4][y + 4] = "-";
                printMap[x + 4][y + 5] = "-";
                printMap[x - 4][y] = "-";
                printMap[x - 4][y + 1] = "-";
                printMap[x - 4][y + 2] = "-";
                printMap[x - 4][y + 3] = "-";
                printMap[x - 4][y + 4] = "-";
                printMap[x - 4][y + 5] = "-";
                printMap[x + 4][y - 1] = "-";
                printMap[x + 4][y - 2] = "-";
                printMap[x + 4][y - 3] = "-";
                printMap[x + 4][y - 4] = "-";
                printMap[x + 4][y - 5] = "-";
                printMap[x - 4][y - 1] = "-";
                printMap[x - 4][y - 2] = "-";
                printMap[x - 4][y - 3] = "-";
                printMap[x - 4][y - 4] = "-";
                printMap[x - 4][y - 5] = "-";
            }
        }
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                System.out.print(printMap[i][j]);
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
