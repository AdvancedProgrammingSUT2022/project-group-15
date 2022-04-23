package controller;

import enums.Direction;
import enums.Improvement;

public class GameMenuController {

    public String changeTurn(){
        // TODO : implement
        return null;
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
        // TODO : implement
        return null;
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

    public String showMap(int x, int y) {
        // TODO : implement
        return null;
    }

    public String showMap(String cityName) {
        // TODO : implement
        return null;
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
