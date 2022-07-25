package server.controller;





import server.enums.*;
import server.model.City;
import server.model.Civilization;
import server.model.Game;
import server.model.Hex;
import server.model.unit.*;

import java.util.ArrayList;

public class GameMenuController {
    private Unit selectedUnit = null;
    private City selectedCity = null;
    private City fallenCity = null;
    private int lastShownMapX = 0;
    private int lastShownMapY = 0;

    public String getAvailableBuildingsForCity() {
        if (selectedCity == null)
            return "error : no city is selected";
        if (selectedCity.getOwner() != Game.getGame().getSelectedCivilization())
            return "error : city not yours";
        StringBuilder ans = new StringBuilder();
        for (Building availableBuilding : selectedCity.getAvailableBuildings()) {
            ans.append(availableBuilding.name).append(" :").append(availableBuilding.productionCost).append("\n");
        }
        if (ans.length() != 0)
            ans.deleteCharAt(ans.length() - 1);
        return ans.toString();
    }

    public String buildBuilding(String buildingName) {
        if (selectedCity == null)
            return "no city is selected";
        if (selectedCity.getOwner() != Game.getGame().getSelectedCivilization())
            return "city not yours";
        Building building = Building.getBuildingByName(buildingName);
        if (building == null)
            return "invalid building name";
        if (!selectedCity.getAvailableBuildings().contains(building))
            return "building not available";
        if ((building == Building.STABLE || building == Building.CIRCUS) && !selectedCity.getOwner().getStrategicResources().contains(Resource.HORSE))
            return "you need horse for this building";
        if ((building == Building.FORGE) && !selectedCity.getOwner().getStrategicResources().contains(Resource.IRON))
            return "you need iron for this building";
        if ((building == Building.FACTORY) && !selectedCity.getOwner().getOpenedResources().contains(Resource.COAL))
            return "you need coal for this building";
        selectedCity.setBuildingUnit(false);
        selectedCity.setBuildingInProgress(building);
        selectedCity.setNeededProduction(building.productionCost);
        return "it will be built";

    }

    public String buyBuilding(String buildingName) {
        if (selectedCity == null)
            return "no city is selected";
        if (selectedCity.getOwner() != Game.getGame().getSelectedCivilization())
            return "city not yours";
        Building building = Building.getBuildingByName(buildingName);
        if (building == null)
            return "invalid building name";
        if (!selectedCity.getAvailableBuildings().contains(building))
            return "building not available";
        if ((building == Building.STABLE || building == Building.CIRCUS) && !selectedCity.getOwner().getStrategicResources().contains(Resource.HORSE))
            return "you need horse for this building";
        if ((building == Building.FORGE) && !selectedCity.getOwner().getStrategicResources().contains(Resource.IRON))
            return "you need iron for this building";
        if ((building == Building.FACTORY) && !selectedCity.getOwner().getOpenedResources().contains(Resource.COAL))
            return "you need coal for this building";
        if (selectedCity.getOwner().getGoldStorage() < building.productionCost)
            return "you dont have enough money";
        selectedCity.getOwner().setGoldStorage(selectedCity.getOwner().getGoldStorage() - building.productionCost);
        selectedCity.createBuildingInCity(building);
        return "it was bought";

    }

    public String changeTurn(boolean ignoreNeedCommand) {
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            if (unit.needsCommand())
                if (!ignoreNeedCommand)
                    return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "some Units Need Command in x: "
                            + unit.getCoordinatesInMap().get('x') / 2 + " y: " + unit.getCoordinatesInMap().get('y'));
        }

        if (Game.getGame().getYear() >= 2050 || Game.getGame().selectedHasAllCapitals())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), showWinner());
        Game.getGame().nextTurn();


        selectedUnit = null;
        selectedCity = null;
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "change turn done \nIt's now your turn " + Game.getGame().getSelectedCivilization().getUser().getNickname() + "!");
    }

    private String showWinner() {
        // TODO: 7/20/2022 scoressssssss
        if (Game.getGame().getYear() < 2050) {
            return "congratulations " + Game.getGame().getSelectedCivilization().getUser().getUsername() + "!!! you won";
        }
        Civilization winner = Game.getGame().getCivilizations().get(0);
        int bestScore = 0;
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            int thisCivScore = 0;
            thisCivScore += civilization.getGoldStorage();
            thisCivScore += civilization.getArea() * 50;
            thisCivScore += civilization.getPopulation() * 10;
            thisCivScore += civilization.getTechnologies().size() * 20;
            if (thisCivScore > bestScore) {
                winner = civilization;
                bestScore = thisCivScore;
            }
        }
        return "congratulations " + winner.getUser().getUsername() + "!!! you won";
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
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String buyNewTechnology(String technologyName) {
        Technology technology = null;
        for (Technology tech : Technology.values()) {
            if (tech.name.equals(technologyName)) {
                technology = tech;
            }
        }

        if (technology == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Invalid technology name!");
        }

        if (!Game.getGame().getSelectedCivilization().getAvailableTechnologies().contains(technology)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "This technology is not available for you! (Open prerequisites first)");
        }

        Game.getGame().getSelectedCivilization().setTechnologyInProgress(technology);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), technologyName + " activated! (is your technology in progress)");
    }

    public void buyNewTechnology(Technology technology) {
        Game.getGame().getSelectedCivilization().setTechnologyInProgress(technology);
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
            message += city.getName() + " population:" + city.getNumberOfCitizen() + " gold per turn:" + city.getGoldPerTurn();
            message += " science per turn:" + city.getSciencePerTurn();
            message += "\n";
        }
        return message;
    }

    public String showDiplomacyPanel() {
        // TODO : implement phase 2
        return null;
    }


    public String showDemographicsPanel() {
        String info = "gold: " + Game.getGame().getSelectedCivilization().getGoldStorage() + " average: " + Game.getGame().getAverageGold() + " best: " + Game.getGame().getBestGold();
        info += "\nunits: " + Game.getGame().getSelectedCivilization().getUnits().size() + " average: " + Game.getGame().getAverageUnit() + " best: " + Game.getGame().getBestUnit();
        info += "\npopulation: " + Game.getGame().getSelectedCivilization().getPopulation() + " average: " + Game.getGame().getAveragePopulation() + " best: " + Game.getGame().getBestPopulation();
        info += "\ncities: " + Game.getGame().getSelectedCivilization().getCities().size() + " average: " + Game.getGame().getAverageCity() + " best: " + Game.getGame().getBestCity();
        info += "\nvastness (number of owned hexes): " + Game.getGame().getSelectedCivilization().getArea() + " average: " + Game.getGame().getAverageArea() + " best: " + Game.getGame().getBestArea();
        info += "\nhappiness: " + Game.getGame().getSelectedCivilization().getHappiness();
        return info;
    }

    public String showNotificationHistory() {
        StringBuilder history = new StringBuilder();
        history.append("<< Notification History >>");
        for (String message : Game.getGame().getNotificationHistory()) {
            history.append("\n").append(message);
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), history.toString());
    }

    public String showMilitaryPanel() {
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), showUnitsPanel());
    }

    public String showEconomyPanel() {
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), showCitiesPanel());
    }

    public String showDealsPanel() {
        // TODO : implement phase 2
        return null;
    }

    public String selectMilitaryUnit(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "not valid coordinate");
        }
        if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit here");

        selectedUnit = Game.getGame().map.map.get(x).get(y).getMilitaryUnit();
        String message = "unit selected!" + "\nowner : " + selectedUnit.getOwner().getUser().getNickname() +
                "\nname : " + selectedUnit.getName() +
                "\nmp : " + selectedUnit.getRemainingMovement() +
                "\nhealth percent : " + selectedUnit.getNowHealth() * 100 / selectedUnit.getTotalHealth();

        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String selectCivilUnit(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "not valid coordinate");
        }
        if (Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit here");

        selectedUnit = Game.getGame().map.map.get(x).get(y).getCivilUnit();
        String message = "unit selected!" + "\nowner : " + selectedUnit.getOwner().getUser().getNickname() +
                "\nname : " + selectedUnit.getName() +
                "\nmp : " + selectedUnit.getRemainingMovement() +
                "\nhealth percent : " + selectedUnit.getNowHealth() * 100 / selectedUnit.getTotalHealth();

        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), message);
    }

    public String selectCity(String cityName) {

        for (Civilization civilization : Game.getGame().getCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(cityName)) {
                    selectedCity = city;
                    return Game.getGame().addNotification(Game.getGame().getTurnNumber(), cityInfo(selectedCity));
                }
            }
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you have no city with this name!");
    }

    public String selectCity(int x, int y) {
        if (!Game.getGame().getSelectedCivilization().getVisibilityMap().validCoordinateInArray(x, y)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "invalid coordinates!");
        }

        if (Game.getGame().map.map.get(x).get(y).getCity() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "here isn't a city!");
        selectedCity = Game.getGame().map.map.get(x).get(y).getCity();
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), cityInfo(selectedCity));
    }

    private String cityInfo(City city) {
        StringBuilder ans = new StringBuilder("city selected!" + "\nname : " + city.getName() +
                "\nowner : " + city.getOwner().getUser().getNickname() +
                "\nhealth percent : " + city.getCityUnit().getNowHealth() * 100 / selectedCity.getCityUnit().getTotalHealth() +
                "\nnumber of citizens : " + city.getNumberOfCitizen() +
                "\nnumber of unemployed citizens : " + city.getUnemployedCitizens() +
                "\nbuilding unit or building : ");
        if (city.isBuildingUnit())
            ans.append(city.getUnitInProgress().name());
        else
            ans.append(city.getBuildingInProgress().name);

        ans.append("\ngold per turn : ").append(city.getGoldPerTurn()).append("\nscience per turn : ").append(city.getSciencePerTurn())
                .append("\nproduction per turn : ").append(city.getProductionPerTurn()).append("\nfood per turn : ").append(city.getFoodPerTurn())
                .append("\nfood storage : ").append(city.getFoodStorage()).append("\nwill build unit in ")
                .append(city.getRemainedTurns()).append(" turns!").append("\nhexes in ");

        for (Hex cityHex : city.getCityHexes()) {
            if (cityHex.isAnyCitizenWorking())
                ans.append(" ( ").append(cityHex.getCoordinatesInArray().get('x')).append(",").append(cityHex.getCoordinatesInArray().get('y')).append(" ) ");
        }
        ans.append(" are being worked");
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), ans.toString());
    }

    public String moveSelectedUnitTo(int x, int y) {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "coordinate not valid");
        if (x == selectedUnit.getCoordinatesInMap().get('x') / 2 && y == selectedUnit.getCoordinatesInMap().get('y'))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is already there");
        double distance = selectedUnit.findShortestPathByDijkstra(x, y);
        if (distance > 999999) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(),
                    "cant go to destination (mountain or ice or sea) or blocked by other units");
        }
        selectedUnit.doPlanedMovement();
        if (selectedUnit.getPlanedToGo() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit moved successfully");
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "planed move will be done");
    }

    public String sleepSelectedUnit() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        selectedUnit.setSleep(true);
        selectedUnit.setPlanedToGo(null);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is slept");

    }

    public String alertSelectedUnit() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        ((MilitaryUnit) selectedUnit).setAlerted(true);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is alerted");
    }

    public String fortifySelectedUnit() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        if (!(((MilitaryUnit) selectedUnit).isFortifying())) {
            ((MilitaryUnit) selectedUnit).setFortifying(true);
            selectedUnit.setMeleePower((int) (selectedUnit.getMeleePower() * 1.5));
        }
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is fortified");
    }

    public String fortifySelectedUnitTillHeal() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        ((MilitaryUnit) selectedUnit).setFortifyingTillHealed(true);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is fortified till healed");

    }

    public String pillage() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (selectedUnit instanceof CivilUnit)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        selectedUnit.setRemainingMovement(-1);
        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2)
                .get(selectedUnit.getCoordinatesInMap().get('y')).setHasDestroyedImprovement(true);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "pillaged!");
    }

    public String garrisonSelectedUnit() {

        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (selectedUnit instanceof CivilUnit) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is a CivilUnit");
        }
        if (Game.getGame().map.isInACity(selectedUnit)) {
            selectedUnit.setRemainingMovement(-1);
            if (!((MilitaryUnit) selectedUnit).isGarrisoning()) {
                ((MilitaryUnit) selectedUnit).setGarrisoning(true);
                ((MilitaryUnit) selectedUnit).garrisonCity();
            }
            discard(true);
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Done");
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not in a city");
    }

    public String setupRangedSelectedUnit() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (!selectedUnit.getName().getCombatType().equals("Siege"))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not siege");

        ((RangedMilitary) selectedUnit).setSetup(true);
        selectedUnit.setRemainingMovement(-1);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is set up");
    }

    public String foundCity() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (!(selectedUnit instanceof SettlerUnit))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "selected unit is not a settler");
        if (Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).
                get(selectedUnit.getCoordinatesInMap().get('y')).getCity() != null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "cant build city in a city!!!");
        ((SettlerUnit) selectedUnit).foundCity();
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city founded successfully!");
    }

    public String cancelSelectedUnitMission() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        selectedUnit.setPlanedToGo(null);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Canceled!");
    }

    public String wakeUpSelectedUnit() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no selected unit");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        }
        if (selectedUnit instanceof CivilUnit)
            selectedUnit.setSleep(false);
        else {
            if (((MilitaryUnit) selectedUnit).isFortifying())
                selectedUnit.setMeleePower((selectedUnit.getMeleePower() * 2 / 3));
            ((MilitaryUnit) selectedUnit).setFortifying(false);
            ((MilitaryUnit) selectedUnit).setFortifyingTillHealed(false);
        }
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is woken up");
    }

    public String deleteSelectedUnit() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Please Select a unit first!");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Unit not yours");
        Game.getGame().getSelectedCivilization().deleteUnit(selectedUnit, true);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Unit Deleted Successfully");
    }


    public String attackTo(int x, int y) {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Please Select a unit first!");
        }
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Unit not yours");
        if (selectedUnit instanceof CivilUnit)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Unit is civil!!!");
        if (selectedUnit.getRemainingMovement() <= 0)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit does not have mp");
        if (selectedUnit.getName().getCombatType().equals("Siege") && !((RangedMilitary) selectedUnit).isSetup())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you should set up Siege unit");
        if (!selectedUnit.unitCanAttack(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "destination is far");
        if (Game.getGame().map.isCenterOfCity(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), attackTo(Game.getGame().map.map.get(x).get(y).getCity()));
        if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null && Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit in destination");
        Unit target = Game.getGame().map.map.get(x).get(y).getMilitaryUnit();
        if (target == null)
            target = Game.getGame().map.map.get(x).get(y).getCivilUnit();
        if (target.getOwner() == selectedUnit.getOwner())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you can't attack yourself");
        ((MilitaryUnit) selectedUnit).attackTo(target);
        declareWar(target.getOwner().getUser().getNickname());
        discard(true);
        discard(false);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "attack is done");
    }

    public String declareWar(String nicknameOfOther) {
        Civilization other = null;
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            if (civilization.getUser().getNickname().equals(nicknameOfOther))
                other = civilization;
        }
        if (other == null)
            return "this not a valid civilization";
        if (other == Game.getGame().getSelectedCivilization())
            return "u cant attack yourself";
        other.getEnemies().add(Game.getGame().getSelectedCivilization());
        Game.getGame().getSelectedCivilization().getEnemies().add(other);
        return "done";
    }

    public String piece(String nicknameOfOther) {
        Civilization other = null;
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            if (civilization.getUser().getNickname().equals(nicknameOfOther))
                other = civilization;
        }
        if (other == null)
            return "this not a valid civilization";
        if (other == Game.getGame().getSelectedCivilization())
            return "u cant make piece with yourself";
        other.getEnemies().remove(Game.getGame().getSelectedCivilization());
        Game.getGame().getSelectedCivilization().getEnemies().remove(other);
        return "done";
    }

    private String attackTo(City city) {
        Unit target = city.getCityUnit();
        if (target.getOwner() == selectedUnit.getOwner())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you can't attack yourself");
        ((MilitaryUnit) selectedUnit).attackTo(target);
        if (target.getNowHealth() <= 0) {
            fallenCity = city;
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city has fallen");
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "attack is done");
    }

    public String buildImprovement(String improvementName) {
        Improvement improvement = Improvement.getImprovementByName(improvementName);
        if (improvement == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "invalid improvement");
        }
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit is selected");
        if (!(selectedUnit instanceof WorkerUnit))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not worker");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is not yours");
        if (!selectedUnit.getOwner().getOpenedImprovements().contains(improvement))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no dont have the required tech for improvement");
        Hex hex = Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y'));
        if (improvement.equals(Improvement.ROAD) || improvement.equals(Improvement.RAILROAD)) {
            ((WorkerUnit) selectedUnit).buildRoad(improvement);
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "it will be done");
        }

        if (!(improvement.features.contains(hex.getFeature()) || improvement.terrains.contains(hex.getTerrain())))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "cant build this improvement here");
        ((WorkerUnit) selectedUnit).buildImprovement(improvement);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "it will be done");
    }

    public String removeJungleOrSwamp() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        Hex hex = Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y'));
        if (!(hex.getFeature().equals(Feature.SWAMP) || hex.getFeature().equals(Feature.JUNGLE) || hex.getFeature().equals(Feature.DENSE_FOREST)))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no jungle or swamp here");
        if (hex.getFeature().equals(Feature.JUNGLE))
            if (!selectedUnit.getOwner().getTechnologies().contains(Technology.MINING))
                return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you need mining technology");
        if (hex.getFeature().equals(Feature.DENSE_FOREST))
            if (!selectedUnit.getOwner().getTechnologies().contains(Technology.BRONZE_WORKING))
                return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you need bronze_working technology");
        if (hex.getFeature().equals(Feature.SWAMP))
            if (!selectedUnit.getOwner().getTechnologies().contains(Technology.MASONRY))
                return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "you need masonry technology");
        ((WorkerUnit) selectedUnit).removeJungle();
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "it will be done");
    }

    public String removeRoute() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        selectedUnit.setRemainingMovement(-1);
        selectedUnit.getOwner().setRoadMaintenance(selectedUnit.getOwner().getRoadMaintenance() - 1);
        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y')).setHasRoad(false);
        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2).get(selectedUnit.getCoordinatesInMap().get('y')).setHasRailRoad(false);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "done");
    }

    public String repair() {
        if (selectedUnit == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "please select a unit first");
        if (!selectedUnit.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit not yours");
        if (!(selectedUnit instanceof WorkerUnit))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "selected unit is not a worker");
        selectedUnit.setRemainingMovement(-1);

        Game.getGame().map.map.get(selectedUnit.getCoordinatesInMap().get('x') / 2)
                .get(selectedUnit.getCoordinatesInMap().get('y')).setHasDestroyedImprovement(false);
        discard(true);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "done");
    }

    public String moveMap(String directionName, int amount) {
        if (directionName.equals("null"))
            return Game.getGame().getSelectedCivilization().showMapOn(lastShownMapX, lastShownMapY);
        Direction direction = Direction.getDirectionByName(directionName);
        if (direction == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "invalid direction");
        }
        int x, y;
        x = lastShownMapX + direction.xDiff * amount;
        y = lastShownMapY + direction.yDiff * amount;
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cant move");
        lastShownMapX = x;
        lastShownMapY = y;
        return Game.getGame().getSelectedCivilization().showMapOn(x, y);
    }

    public String showMapOnPosition(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");

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
                        Game.getGame().addNotification(Game.getGame().getTurnNumber(), "map showed successfully");
                        return Game.getGame().getSelectedCivilization().showMapOn(xOfCity, yOfCity);
                    }
                }
            }
        }

        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "No city with this name");
    }

    public String lockCitizenToHex(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");
        if (Game.getGame().map.map.get(x).get(y).getCity() == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "hex is not a part of a city");
        if (!Game.getGame().map.map.get(x).get(y).getCity().equals(selectedCity))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "hex not in this city");

        if (selectedCity.getUnemployedCitizens() == 0)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unemployed citizen");
        if (Game.getGame().map.map.get(x).get(y).isAnyCitizenWorking())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "tile is being worked");

        selectedCity.lockCitizenToHex(x, y);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "done! citizen is working");

    }

    public String removeCitizenFromWork(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");
        if (!Game.getGame().map.map.get(x).get(y).getCity().equals(selectedCity))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "hex not in this city");

        if (!Game.getGame().map.map.get(x).get(y).isAnyCitizenWorking())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "tile is not being worked");

        selectedCity.removeCitizenFromHex(x, y);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "done! citizen is not working any more");
    }

    public String buyHex(int x, int y) {
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Coordinate not valid");
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");

        if (Game.getGame().map.map.get(x).get(y).getCity() != null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "hex is part of a city");
        if (!Game.getGame().map.hasTheCityAround(x, y, selectedCity))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "not near city");
        selectedCity.buyHex(x, y);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "hex is bought");
    }

    public String buyUnit(String unitName) {
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");
        UnitName unit = UnitName.getUnitNameByName(unitName);
        if (unit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "invalid unit name");
        }
        if (!selectedCity.getOwner().getOpenedUnits().contains(unit)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Not proper technology");
        }
        if (!selectedCity.getOwner().getStrategicResources().contains(unit.getResource())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Not proper resource");
        }

        if (selectedCity.getOwner().getGoldStorage() < unit.getCost())
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "not enough money");
        Hex center = Game.getGame().map.map.get(selectedCity.getCoordinatesOfCenterInArray().get('x'))
                .get(selectedCity.getCoordinatesOfCenterInArray().get('y'));
        if (unit.getCombatType().equals("Civilian")) {
            if (center.getCivilUnit() != null)
                return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "civil unit is full");
        }
        if (!unit.getCombatType().equals("Civilian")) {
            if (center.getMilitaryUnit() != null)
                return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "military unit is full");
        }
        selectedCity.getOwner().setGoldStorage(selectedCity.getOwner().getGoldStorage() - unit.getCost());
        selectedCity.createUnitInCity(unit);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "unit is bought");
    }

    public String cityAttackTo(int x, int y) {
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");
        selectedUnit = selectedCity.getCityUnit();
        return attackTo(x, y);
    }

    public String chooseProductionForUnit(String unitName) {
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "city not yours");
        UnitName unit = UnitName.getUnitNameByName(unitName);
        if (unit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "invalid unit name");
        }
        if (!selectedCity.getOwner().getOpenedUnits().contains(unit)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Not proper technology");
        }
        if (!selectedCity.getOwner().getStrategicResources().contains(unit.getResource())) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Not proper resource");
        }
        if (unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getCivilUnit() != null)
                    return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "civil unit is full");
            }
        }
        if (!unit.getCombatType().equals("Civilian")) {
            for (Hex cityHex : selectedCity.getCityHexes()) {
                if (cityHex.getMilitaryUnit() != null)
                    return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "military unit is full");
            }
        }
        selectedCity.setBuildingUnit(true);
        selectedCity.setUnitInProgress(unit);
        selectedCity.setNeededProduction(unit.getCost());
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "it will be produced");

    }

    public String cheatIncreaseTurn(int amount) {
        for (int i = 0; i < amount; i++) {
            changeTurn(true);
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : " + amount + " turns passed");
    }

    public String cheatIncreaseGold(int amount) {
        Game.getGame().getSelectedCivilization().setGoldStorage(Game.getGame().getSelectedCivilization().getGoldStorage() + amount);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your gold storage increased by " + amount);
    }

    public String cheatIncreaseScience(int amount) {
        Game.getGame().getSelectedCivilization().setScienceStorage(Game.getGame().getSelectedCivilization().getScienceStorage() + amount);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your science storage increased by " + amount);
    }

    public String cheatIncreaseCitizens(int amount) {
        for (City city : Game.getGame().getSelectedCivilization().getCities()) {
            city.setNumberOfCitizen(city.getNumberOfCitizen() + amount);
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Each city gained " + amount + " citizens");
    }

    public String cheatIncreaseScore(int amount) {
        Game.getGame().getSelectedCivilization().getUser().changeScore(amount);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Your score increased by " + amount);
    }

    public String cheatOpenAllTechnologies() {
        for (Technology technology : Technology.values()) {
            Game.getGame().getSelectedCivilization().getTechnologies().add(technology);
            Game.getGame().getSelectedCivilization().getOpenedUnits().addAll(technology.openingUnits);
            Game.getGame().getSelectedCivilization().getOpenedImprovements().addAll(technology.openingImprovements);
            Game.getGame().getSelectedCivilization().getOpenedResources().addAll(technology.openingResources);
            Game.getGame().getSelectedCivilization().getOpenedBuildings().addAll(technology.openingBuildings);
        }
        for (City city : Game.getGame().getSelectedCivilization().getCities()) {
            city.updateAvailableBuildings();
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : All technologies are opened for you!");
    }

    public String cheatMakeMapDetermined() {
        for (ArrayList<Hex> hexes : Game.getGame().getSelectedCivilization().getVisibilityMap().map) {
            for (Hex hex : hexes) {
                hex.setHexVisibility(HexVisibility.DETERMINED);
            }
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : The whole map is now determined for you!");
    }

    public String cheatWin() {
        Game.getGame().setYear(3000);
        Game.getGame().getSelectedCivilization().setGoldStorage(999999999);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : You won!");
    }

    public String cheatFoundCityOn(int x, int y) {
        SettlerUnit settlerUnit = new SettlerUnit(x, y, Game.getGame().getSelectedCivilization(), UnitName.SETTLER);
        settlerUnit.foundCity();
        return null;
    }

    public String cheatIncreaseHealthOfSelectedUnit() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit selected!");
        }
        selectedUnit.setNowHealth(selectedUnit.getTotalHealth());
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Health increased");
    }

    public String cheatIncreasePowerOfSelectedUnit() {
        if (selectedUnit == null) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "no unit selected!");
        }
        if (!(selectedUnit instanceof MilitaryUnit)) {
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "selected unit is not military!");
        }
        if (selectedUnit instanceof MeleeMilitary) {
            selectedUnit.setMeleePower((int) (selectedUnit.getMeleePower() * 1.5));
        }
        if (selectedUnit instanceof RangedMilitary) {
            ((RangedMilitary) selectedUnit).setRangedPower((int) (((RangedMilitary) selectedUnit).getRangedPower() * 1.5));
        }
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "Cheat code accepted : Power of attack increased");
    }

    public String captureCity() {
        int x = fallenCity.getCoordinatesOfCenterInArray().get('x');
        int y = fallenCity.getCoordinatesOfCenterInArray().get('y');
        SettlerUnit settlerUnit = new SettlerUnit(x, y, Game.getGame().getSelectedCivilization(), UnitName.SETTLER);
        settlerUnit.foundCity();
        if (Game.getGame().getOriginalCapitals().contains(fallenCity)) {
            Game.getGame().getOriginalCapitals().remove(fallenCity);
            City newCity = Game.getGame().getSelectedCivilization().getCities().get(Game.getGame().getSelectedCivilization().getCities().size() - 1);
            Game.getGame().getOriginalCapitals().add(newCity);
            fallenCity = null;
            return "you captured a capital !!!";
        } else {
            fallenCity = null;
            return "city is captured";
        }


    }

    public void discard(boolean isUnit) {
        if (isUnit)
            selectedUnit = null;
        else
            selectedCity = null;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public String getAvailableUnitsInCity() {
        if (selectedCity == null)
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "error : no city is selected");
        if (!selectedCity.getOwner().equals(Game.getGame().getSelectedCivilization()))
            return Game.getGame().addNotification(Game.getGame().getTurnNumber(), "error : city not yours");
        StringBuilder message = new StringBuilder();
        for (UnitName unit : Game.getGame().getSelectedCivilization().getOpenedUnits()) {
            message.append(unit.getName()).append("\n");
        }
        message.deleteCharAt(message.length() - 1);
        return Game.getGame().addNotification(Game.getGame().getTurnNumber(), message.toString());
    }

    public String save() {
        return Game.getGame().saveGame();
    }

    public void clean() {
        Game.setGame(null);
    }

    public String destroyCity() {
        if (Game.getGame().getOriginalCapitals().contains(fallenCity)) {
            captureCity();
            return "city is capital u can only capture it";
        }
        return "destroyed !!!";
    }

    public String selectUnit(int turn) {
        selectedUnit = Game.getGame().getSelectedCivilization().getUnits().get(turn - 1);
        if (selectedUnit instanceof CivilUnit)
            return selectCivilUnit(selectedUnit.getCoordinatesInMap().get('x') / 2, selectedUnit.getCoordinatesInMap().get('y'));
        else
            return selectMilitaryUnit(selectedUnit.getCoordinatesInMap().get('x') / 2, selectedUnit.getCoordinatesInMap().get('y'));

    }

    public City selectCity(int turn) {

        return Game.getGame().getSelectedCivilization().getCities().get(turn - 1);
    }

    public String trade(String resourceYouGetName, String resourceYouLossName, String goldYouGet, String goldYouLoss, String usernameOfOther) {
        Civilization other = null;

        for (Civilization civilization : Game.getGame().getCivilizations()) {
            if (civilization.getUser().getUsername().equals(usernameOfOther))
                other = civilization;
        }

        int goldDifference;
        try {
            goldDifference = Integer.parseInt(goldYouGet) - Integer.parseInt(goldYouLoss);
        } catch (Exception e) {
            return "format of gold is invalid";
        }
        Game.getGame().getSelectedCivilization().setGoldStorage(Game.getGame().getSelectedCivilization().getGoldStorage() + goldDifference);
        other.setGoldStorage(other.getGoldStorage() - goldDifference);

        Resource resourceYouGet = Resource.getByName(resourceYouGetName);
        if (resourceYouGet != null)
            getResourceFromTo(other, Game.getGame().getSelectedCivilization(), resourceYouGet);
        Resource resourceYouLoss = Resource.getByName(resourceYouLossName);
        if (resourceYouLoss != null)
            getResourceFromTo(Game.getGame().getSelectedCivilization(), other, resourceYouLoss);

        return "done";
    }

    private void getResourceFromTo(Civilization from, Civilization to, Resource resource) {
        if (resource.type.equals("luxury")) {
            from.getLuxuryResources().remove(resource);
            to.getLuxuryResources().add(resource);
        } else {
            from.getStrategicResources().remove(resource);
            to.getStrategicResources().add(resource);
        }
    }
}
