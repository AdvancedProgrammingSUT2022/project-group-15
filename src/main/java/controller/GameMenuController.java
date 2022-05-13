package controller;

import enums.*;
import model.*;

import model.unit.*;

import java.util.ArrayList;

public class GameMenuController {
    private Unit selectedUnit = null;
    private City selectedCity = null;
    private int lastShownMapX = 0;
    private int lastShownMapY = 0;

    public String changeTurn() {
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            if (unit.needsCommand())
                return Controller.addNotification(Game.getGame().getTurnNumber()
                        , "some Units Need Command in x: " + unit.getCoordinatesInMap().get('x') / 2 + " y: " + unit.getCoordinatesInMap().get('y'));
        }

        Game.getGame().nextTurn();
        selectedUnit = null;
        selectedCity = null;
        return Controller.addNotification(Game.getGame().getTurnNumber(), "change turn done \nIt's now your turn " + Game.getGame().getSelectedCivilization().getUser().getNickname() + "!");
    }

    /**
     * shows the technology info and the available technologies
     *
     * @return message to be shown
     * @author Parsa
     */
    public String showTechnologyInfo() {
        String message = "";
        // current technology
        Technology technology = Game.getGame().getSelectedCivilization().getTechnologyInProgress();
        if (technology != null) {
            int remainingTurns = (int) Math.ceil((technology.cost - Game.getGame().getSelectedCivilization().getScienceStorage())
                    / (double) Game.getGame().getSelectedCivilization().getSciencePerTurn());
            message = "Current Technology : " + technology + "( " + remainingTurns + " turns remaining to achieve )\n";
        } else {
            message = "No Current Technology\n";
        }

        // available technologies
        Game.getGame().getSelectedCivilization().updateAvailableTechnologies();
        ArrayList<Technology> availableTechs = Game.getGame().getSelectedCivilization().getAvailableTechnologies();
        message += "Available Technologies :\n";
        for (int i = 0; i < availableTechs.size(); i++) {
            message += (i + 1) + ") " + availableTechs.get(i) + "\n";
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String buyNewTechnology(String technologyName) {
        Technology technology = null;
        for (Technology tech : Technology.values()) {
            if (tech.toString().equals(technologyName)) {
                technology = tech;
            }
        }

        if (technology == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Invalid technology name!");
        }

        if (!Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "This technology is not available for you! (Open prerequisites first)");
        }

        Game.getGame().getSelectedCivilization().setTechnologyInProgress(technology);
        return Controller.addNotification(Game.getGame().getTurnNumber(), technologyName + " activated! (is your technology in progress)");
    }

    public String showUnitsPanel() {
        String message = "";
        int number = 1;
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            message += number + ") name:" + unit.getName().name() + " x:" + unit.getCoordinatesInMap().get('x') / 2 +
                    " y:" + unit.getCoordinatesInMap().get('y') + " health percent:" + unit.getNowHealth() * 100 / unit.getTotalHealth();
            message += "\n";
            number++;
        }
        return message;
    }

    public String showCitiesPanel() {
        String message = "";
        for (City city : Game.getGame().getSelectedCivilization().getCities()) {
            message += cityInfo(city);
            message += "\n";
        }
        return message;
    }

    public String showDiplomacyPanel() {
        // TODO : implement phase 2
        return null;
    }

    public String showVictoryPanel() {
        // TODO : implement phase 2
        return null;
    }

    public String showDemographicsPanel() {
        String info = "gold: " + Game.getGame().getSelectedCivilization().getGoldStorage() + " average: " + Game.getGame().getAverageGold() + " best: " + Game.getGame().getBestGold();
        info += "\nunits: " + Game.getGame().getSelectedCivilization().getUnits().size() + " average: " + Game.getGame().getAverageUnit() + " best: " + Game.getGame().getBestUnit();
        info += "\npopulation: " + Game.getGame().getSelectedCivilization().getPopulation() + " average: " + Game.getGame().getAveragePopulation() + " best: " + Game.getGame().getBestPopulation();
        info += "\ncities: " + Game.getGame().getSelectedCivilization().getCities().size() + " average: " + Game.getGame().getAverageCity() + " best: " + Game.getGame().getBestCity();
        info += "\nvastness (number of owned hexes): " + Game.getGame().getSelectedCivilization().getArea() + " average: " + Game.getGame().getAverageArea() + " best: " + Game.getGame().getBestArea();
        return info;
    }

    public String showNotificationHistory() {
        StringBuilder history = new StringBuilder();
        history.append("<< Notification History >>");
        for (String message : Controller.getNotificationHistory()) {
            history.append("\n").append(message);
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), history.toString());
    }

    public String showMilitaryPanel() {
        // TODO : implement phase 2
        return showUnitsPanel();
    }

    public String showEconomyPanel() {
        // TODO : implement phase 2
        return showCitiesPanel();
    }

    public String showDealsPanel() {
        // TODO : implement phase 2
        return null;
    }

    public String selectMilitaryUnit(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "not valid coordinate");
        }
        if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unit here");

        selectedUnit = Game.getGame().map.map.get(x).get(y).getMilitaryUnit();
        String message = "unit selected!" + "\nowner : " + selectedUnit.getOwner().getUser().getNickname() +
                "\nname : " + selectedUnit.getName() +
                "\nhealth percent : " + selectedUnit.getNowHealth() * 100 / selectedUnit.getTotalHealth();

        return Controller.addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String selectCivilUnit(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "not valid coordinate");
        }
        if (Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unit here");

        selectedUnit = Game.getGame().map.map.get(x).get(y).getCivilUnit();
        String message = "unit selected!" + "\nowner : " + selectedUnit.getOwner().getUser().getNickname() +
                "\nname : " + selectedUnit.getName() +
                "\nhealth percent : " + selectedUnit.getNowHealth() * 100 / selectedUnit.getTotalHealth();

        return Controller.addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String selectCity(String cityName) {

        for (Civilization civilization : Game.getGame().getCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(cityName)) {
                    selectedCity = city;
                    return Controller.addNotification(Game.getGame().getTurnNumber(), cityInfo(selectedCity));
                }
            }
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "you have no city with this name!");
    }

    public String selectCity(int x, int y) {
        if (!Game.getGame().getSelectedCivilization().getVisibilityMap().validCoordinateInArray(x, y)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "invalid coordinates!");
        }

        if (Game.getGame().map.map.get(x).get(y).getCity() == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "here isn't a city!");
        selectedCity = Game.getGame().map.map.get(x).get(y).getCity();
        return Controller.addNotification(Game.getGame().getTurnNumber(), cityInfo(selectedCity));
    }

    private String cityInfo(City city) {
        return "city selected!" + "\nname : " + city.getName() +
                "\nowner : " + city.getOwner().getUser().getNickname() +
                "\nhealth percent : " + city.getCityUnit().getNowHealth() * 100 / selectedCity.getCityUnit().getTotalHealth() +
                "\nnumber of citizens : " + city.getNumberOfCitizen() +
                "\nbuilding unit : " + city.getUnitInProgress().name() +
                "\ngold per turn : " + city.getGoldPerTurn() +
                "\nscience per turn : " + city.getSciencePerTurn() +
                "\nproduction per turn : " + city.getProductionPerTurn() +
                "\nfood per turn : " + city.getFoodPerTurn() +
                "\nfood storage : " + city.getFoodStorage() +
                "\nwill build unit in " + (city.getRemainedTurns()) + " turns!";
    }

    public String moveSelectedUnitTo(int x, int y) {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        double distance = selectedUnit.findShortestPathByDijkstra(x, y);
        if (distance > 999999) {
            return Controller.addNotification(Game.getGame().getTurnNumber(),
                    "cant go to destination (mountain or ice or sea) or blocked by other units");
        }
        selectedUnit.doPlanedMovement();
        if (selectedUnit.getPlanedToGo() == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit moved successfully");
        return Controller.addNotification(Game.getGame().getTurnNumber(), "planed move will be done");
    }

    public String sleepSelectedUnit() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        selectedUnit.setSleep(true);
        selectedUnit.setPlanedToGo(null);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is slept");

    }

    public String alertSelectedUnit() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        ((MilitaryUnit) selectedUnit).setAlerted(true);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is alerted");

    }


    public String fortifySelectedUnit() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        if (!(((MilitaryUnit) selectedUnit).isFortifying())) {
            ((MilitaryUnit) selectedUnit).setFortifying(true);
            selectedUnit.setMeleePower((int) (selectedUnit.getMeleePower() * 1.5));
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is fortified");


    }

    public String fortifySelectedUnitTillHeal() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        ((MilitaryUnit) selectedUnit).setFortifyingTillHealed(true);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is fortified till healed");

    }

    public String pillage() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (selectedUnit instanceof CivilUnit)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        selectedUnit.setRemainingMovement(-1);
        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2)
                .get(selectedUnit.getCoordinatesInMap().get('y')).setHasDestroyedImprovement(true);
        return "done";

    }

    public String garrisonSelectedUnit() {

        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (selectedUnit instanceof CivilUnit) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        if (Game.getGame().map.isInACity(selectedUnit)) {
            selectedUnit.setRemainingMovement(-1);
            if (!((MilitaryUnit) selectedUnit).isGarrisoning()) {
                ((MilitaryUnit) selectedUnit).setGarrisoning(true);
                ((MilitaryUnit) selectedUnit).garrisonCity();
            }
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Done");
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not in a city");

    }

    public String setupRangedSelectedUnit() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (!selectedUnit.getName().getCombatType().equals("Siege"))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is not siege");
        ((RangedMilitary) selectedUnit).setSetup(true);
        selectedUnit.setRemainingMovement(-1);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is set up");
    }

    public String foundCity() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (!(selectedUnit instanceof SettlerUnit))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "selected unit is not a settler");
        if (Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).
                get(selectedUnit.getCoordinatesInMap().get('y')).getCity() != null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "cant build city in a city!!!");
        ((SettlerUnit) selectedUnit).foundCity();
        return Controller.addNotification(Game.getGame().getTurnNumber(), "city founded successfully!");
    }

    public String cancelSelectedUnitMission() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        selectedUnit.setPlanedToGo(null);
        return "Done";
    }

    public String wakeUpSelectedUnit() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit)
            selectedUnit.setSleep(false);
        else {
            if (((MilitaryUnit) selectedUnit).isFortifying())
                selectedUnit.setMeleePower((selectedUnit.getMeleePower() * 2 / 3));
            ((MilitaryUnit) selectedUnit).setFortifying(false);
            ((MilitaryUnit) selectedUnit).setFortifyingTillHealed(false);
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is woken up");
    }

    public String deleteSelectedUnit() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Please Select a unit first!");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Unit not yours");
        Game.getGame().getSelectedCivilization().deleteUnit(selectedUnit, true);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Unit Deleted Successfully");
    }


    public String attackTo(int x, int y) {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Please Select a unit first!");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Unit not yours");
        if (selectedUnit instanceof CivilUnit)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Unit is civil!!!");
        if (selectedUnit.getRemainingMovement() <= 0)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit does not have mp");
        if (selectedUnit.getName().getCombatType().equals("Siege") && !((RangedMilitary) selectedUnit).isSetup())
            return Controller.addNotification(Game.getGame().getTurnNumber(), "you should set up Siege unit");
        if (!selectedUnit.unitCanAttack(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "destination is far");
        if (Game.getGame().map.isCenterOfCity(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), attackTo(Game.getGame().map.map.get(x).get(y).getCity()));
        if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null && Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unit in destination");
        Unit target = Game.getGame().map.map.get(x).get(y).getMilitaryUnit();
        if (target == null)
            target = Game.getGame().map.map.get(x).get(y).getCivilUnit();
        if (target.getOwner() == selectedUnit.getOwner())
            return Controller.addNotification(Game.getGame().getTurnNumber(), "you can't attack yourself");
        ((MilitaryUnit) selectedUnit).attackTo(target);

        return Controller.addNotification(Game.getGame().getTurnNumber(), "attack is done");
    }

    private String attackTo(City city) {
        Unit target = city.getCityUnit();
        if (target.getOwner() == selectedUnit.getOwner())
            return "you can't attack yourself";
        ((MilitaryUnit) selectedUnit).attackTo(target);
        return "attack is done";
    }

    public String buildImprovement(String improvementName) {
        Improvement improvement = Improvement.getImprovementByName(improvementName);
        if (improvement == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "invalid improvement");
        }
        // TODO : implement
        return null;
    }

    public String removeJungle() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        // remove dense-forests requires Bronze-Working Technology
        // remove jungles requires Mining Technology
        // TODO : implement
        return null;
    }

    public String removeRoute() {

        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        selectedUnit.setRemainingMovement(-1);

        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y')).setHasRoad(false);
        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y')).setHasRailRoad(false);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "done");
    }

    public String repair() {
        if (selectedUnit == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        selectedUnit.setRemainingMovement(-1);

        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2)
                .get(selectedUnit.getCoordinatesInMap().get('y')).setHasDestroyedImprovement(false);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "done");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////show whole map////////////////////////////////////////////////////////////////////////
    public String showMap() {
        String[][] printMap = new String[GlobalThings.mapHeight][GlobalThings.mapWidth];
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                printMap[i][j] = GlobalThings.BLACK + '█';
            }
        }
        for (int i = 0; i < Game.getGame().getRows() * 2; i++) {
            for (int j = 0; j < Game.getGame().getColumns() / 2; j++) {
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
                fillHexWithInfo(printMap, x, y, i / 2, 2 * j + i % 2);
            }
        }
        for (int i = 0; i < GlobalThings.mapHeight; i++) {
            for (int j = 0; j < GlobalThings.mapWidth; j++) {
                System.out.print(printMap[i][j]);
            }
            System.out.println("");
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "print map successfully");
    }

    private void fillHexWithInfo(String[][] printMap, int x, int y, int mapArrayX, int mapArrayY) {
        preliminaryInfo(printMap, x, y, mapArrayX, mapArrayY);

        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getCivilUnit() == null) {
            replaceText(printMap, x, y, -2, "CiU", "N/A", GlobalThings.RED);
        } else if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getCivilUnit() instanceof WorkerUnit) {
            replaceText(printMap, x, y, -2, "CiU", "WOR", GlobalThings.RED);
        } else {
            replaceText(printMap, x, y, -2, "CiU", "SET", GlobalThings.RED);
        }

        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getMilitaryUnit() == null) {
            replaceText(printMap, x, y, -1, "MiU", "N/A", GlobalThings.RED);
        } else {
            replaceText(printMap, x, y, -1, "MiU", Game.getGame().map.map.get(mapArrayX).get(mapArrayY)
                    .getMilitaryUnit().getName().toString().substring(0, 3), GlobalThings.RED);
        }

        replaceText(printMap, x, y, 0, "TER",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getTerrain().name.substring(0, 3), GlobalThings.YELLOW);


        replaceText(printMap, x, y, +1, "FET",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getFeature().name.substring(0, 3), GlobalThings.YELLOW);

        replaceText(printMap, x, y, +2, "RES",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getResource().name.substring(0, 3), GlobalThings.YELLOW);


        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).isHasRoad()) {
            if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).isHasRailRoad())
                replaceText(printMap, x, y, +3, "ROD", "RAL", GlobalThings.BLACK);
            else
                replaceText(printMap, x, y, +3, "ROD", "ROD", GlobalThings.BLACK);
        } else {
            replaceText(printMap, x, y, +3, "ROD", "N/A", GlobalThings.WHITE_BACKGROUND + GlobalThings.BLACK);
        }

    }

    private void preliminaryInfo(String[][] printMap, int x, int y, int mapArrayX, int mapArrayY) {
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

        printMap[x - 3][y] = ",";
        printMap[x - 3][y - 1] = "";
        printMap[x - 3][y + 2] = "";
        printMap[x - 3][y - 2] = GlobalThings.BLUE + String.format("%02d", mapArrayX);
        printMap[x - 3][y + 1] = String.format("%02d", mapArrayY);
    }

    private void replaceText(String[][] map, int x, int y, int xDiff, String firstThree, String secondThree, String
            color) {
        map[x + xDiff][y] = ":";
        map[x + xDiff][y - 1] = "";
        map[x + xDiff][y - 2] = "";
        map[x + xDiff][y - 3] = color + firstThree;
        map[x + xDiff][y + 1] = secondThree;
        map[x + xDiff][y + 2] = "";
        map[x + xDiff][y + 3] = "";
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////^^^show whole map^^^//////////////////////////


    public String moveMap(String directionName, int amount) {
        if (directionName.equals("null"))
            return Game.getGame().getSelectedCivilization().showMapOn(lastShownMapX, lastShownMapY);
        Direction direction = Direction.getDirectionByName(directionName);
        if (direction == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "invalid direction");
        }
        int x, y;
        x = lastShownMapX + direction.xDiff * amount;
        y = lastShownMapY + direction.yDiff * amount;
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Cant move");
        lastShownMapX = x;
        lastShownMapY = y;
        return Game.getGame().getSelectedCivilization().showMapOn(x, y);
    }

    public String showMapOnPosition(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");

        lastShownMapX = x;
        lastShownMapY = y;
        return Game.getGame().getSelectedCivilization().showMapOn(x, y);
    }

    public String showMapOnCity(String cityName) {
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(cityName)) {
                    int xOfCity = city.getCoordinatesOfCenterInArray().get('x');
                    int yOfCity = city.getCoordinatesOfCenterInArray().get('y');
                    if (Game.getGame().getSelectedCivilization().getVisibilityMap().map.get(xOfCity).get(yOfCity)
                            .getHexVisibility().equals(HexVisibility.FOG_OF_WAR))
                        return "you haven't seen " + cityName;
                    else {
                        lastShownMapX = xOfCity;
                        lastShownMapY = yOfCity;
                        Controller.addNotification(Game.getGame().getTurnNumber(), "map showed successfully");
                        return Game.getGame().getSelectedCivilization().showMapOn(xOfCity, yOfCity);
                    }
                }
            }
        }

        return Controller.addNotification(Game.getGame().getTurnNumber(), "No city with this name");
    }

    public String lockCitizenToHex(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");
        if (Game.getGame().map.map.get(x).get(y).getCity().equals(selectedCity))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "hex not in this city");

        if (selectedCity.getUnemployedCitizens() == 0)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unemployed citizen");
        if (Game.getGame().map.map.get(x).get(y).isAnyCitizenWorking())
            return Controller.addNotification(Game.getGame().getTurnNumber(), "tile is being worked");

        selectedCity.lockCitizenToHex(x, y);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "done! citizen is working");

    }

    public String removeCitizenFromWork(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");
        if (Game.getGame().map.map.get(x).get(y).getCity().equals(selectedCity))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "hex not in this city");

        if (!Game.getGame().map.map.get(x).get(y).isAnyCitizenWorking())
            return Controller.addNotification(Game.getGame().getTurnNumber(), "tile is not being worked");


        selectedCity.removeCitizenFromHex(x, y);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "done! citizen is not working any more");
    }

    public String buyHex(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");

        if (Game.getGame().map.map.get(x).get(y).getCity() != null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "hex is part of a city");
        if (!Game.getGame().map.hasTheCityAround(x, y, selectedCity))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "not near city");
        selectedCity.buyHex(x, y);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "hex is bought");

    }

    public String buyUnit(String unitName) {
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");
        UnitName unit = UnitName.getUnitNameByName(unitName);
        if (unit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "invalid unit name");
        }
        if (selectedCity.getOwner().getOpenedUnits().contains(unit)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Not proper technology");
        }

        if (selectedCity.getOwner().getGoldStorage() < unit.getCost())
            return Controller.addNotification(Game.getGame().getTurnNumber(), "not enough money");
        if (unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getCivilUnit() != null)
                    return Controller.addNotification(Game.getGame().getTurnNumber(), "civil unit is full");
            }
        }
        if (!unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getMilitaryUnit() != null)
                    return Controller.addNotification(Game.getGame().getTurnNumber(), "military unit is full");
            }
        }
        if (Game.getGame().getSelectedCivilization().getOpenedUnits().contains(unit)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "This unit is not available for you");
        }
        selectedCity.getOwner().setGoldStorage(selectedCity.getOwner().getGoldStorage() - unit.getCost());
        selectedCity.createUnitInCity(unit);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "unit is bought");
    }

    public String cityAttackTo(int x, int y) {
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");
        selectedUnit = selectedCity.getCityUnit();
        return attackTo(x, y);
    }

    public String chooseProductionForUnit(String unitName) {
        if (selectedCity == null)
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Controller.addNotification(Game.getGame().getTurnNumber(), "city not yours");
        UnitName unit = UnitName.getUnitNameByName(unitName);
        if (unit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "invalid unit name");
        }
        if (selectedCity.getOwner().getOpenedUnits().contains(unit)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "Not proper teechnology");
        }
        if (unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getCivilUnit() != null)
                    return Controller.addNotification(Game.getGame().getTurnNumber(), "civil unit is full");
            }
        }
        if (!unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getMilitaryUnit() != null)
                    return Controller.addNotification(Game.getGame().getTurnNumber(), "military unit is full");
            }
        }
        selectedCity.setUnitInProgress(unit);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "it will be produced");

    }

    public String cheatIncreaseTurn(int amount) {
        for (int i = 0; i < amount; i++) {
            changeTurn();
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : " + amount + " turns passed");
    }

    public String cheatIncreaseGold(int amount) {
        Game.getGame().getSelectedCivilization().setGoldStorage(Game.getGame().getSelectedCivilization().getGoldStorage() + amount);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your gold storage increased by " + amount);
    }

    public String cheatIncreaseScience(int amount) {
        Game.getGame().getSelectedCivilization().setScienceStorage(Game.getGame().getSelectedCivilization().getScienceStorage() + amount);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your science storage increased by " + amount);
    }

    public String cheatIncreaseCitizens(int amount) {
        for (City city : Game.getGame().getSelectedCivilization().getCities()) {
            city.setNumberOfCitizen(city.getNumberOfCitizen() + amount);
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Each city gained " + amount + " citizens");
    }

    public String cheatIncreaseScore(int amount) {
        Game.getGame().getSelectedCivilization().getUser().changeScore(amount);
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your score increased by " + amount);
    }

    public String cheatOpenAllTechnologies() {
        for (Technology technology : Technology.values()) {
            Game.getGame().getSelectedCivilization().getTechnologies().add(technology);
            Game.getGame().getSelectedCivilization().getOpenedUnits().addAll(technology.openingUnits);
            Game.getGame().getSelectedCivilization().getOpenedFeatures().addAll(technology.openingFeatures);
            Game.getGame().getSelectedCivilization().getOpenedImprovements().addAll(technology.openingImprovements);
            Game.getGame().getSelectedCivilization().getOpenedResources().addAll(technology.openingResources);
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : All technologies are opened for you!");
    }

    public String cheatMakeMapDetermined() {
        for (ArrayList<Hex> hexes : Game.getGame().getSelectedCivilization().getVisibilityMap().map) {
            for (Hex hex : hexes) {
                hex.setHexVisibility(HexVisibility.DETERMINED);
            }
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : The whole map is now determined for you!");
    }

    public String cheatWin() {
        // TODO : Phase 2 : implement
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : You won!");
    }

    public String cheatFoundCityOn(int x, int y) {
        SettlerUnit settlerUnit = new SettlerUnit(x, y, Game.getGame().getSelectedCivilization(), UnitName.SETTLER);
        settlerUnit.foundCity();
        return null;
    }

    public String cheatIncreaseHealthOfSelectedUnit() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unit selected!");
        }
        selectedUnit.setNowHealth(selectedUnit.getTotalHealth());
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Health increased");
    }

    public String cheatIncreasePowerOfSelectedUnit() {
        if (selectedUnit == null) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "no unit selected!");
        }
        if (!(selectedUnit instanceof MilitaryUnit)) {
            return Controller.addNotification(Game.getGame().getTurnNumber(), "selected unit is not military!");
        }
        if (selectedUnit instanceof MeleeMilitary) {
            selectedUnit.setMeleePower((int) (selectedUnit.getMeleePower() * 1.5));
        }
        if (selectedUnit instanceof RangedMilitary) {
            ((RangedMilitary) selectedUnit).setRangedPower((int) (((RangedMilitary) selectedUnit).getRangedPower() * 1.5));
        }
        return Controller.addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Power of attack increased");
    }


    // TODO : implement removing Swamp ( that requires Masonry Technology )
}
