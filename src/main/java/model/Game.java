package model;

import controller.GameMenuController;
import enums.Feature;
import enums.NeighborHex;
import enums.Resource;
import enums.Terrain;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Game {
    private static Game game;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;
    private Civilization selectedCivilization;
    public Map map;


    private Game() {
        turn = 0;
    }

    public static Game getGame() {
        return game;
    }

    public Civilization getSelectedCivilization() {
        return selectedCivilization;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public static void startNewGame(ArrayList<User> users) {
        game = new Game();
        game.map = new Map(10, 10); //this constants might change later or be given by user
        //System.out.println(Game.getGame().getRows());
        game.map.fillMap();
        for (User user : users) {
            game.civilizations.add(new Civilization(user));
        }
        for (Civilization civilization : game.civilizations) {
            civilization.setUp();
        }
        game.selectedCivilization = game.civilizations.get(0);
    }

    public int getRows() {
        return map.getRowsNumber();
    }

    public int getColumns() {
        return map.getColumnsNumber();
    }


    public void nextTurn() {
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            unit.resetMovement();
        }
        for (City city : Game.getGame().getSelectedCivilization().getCities()) {
            city.moveToNextTurn();
        }
        selectedCivilization.nextTurn();
        turn++;
        selectedCivilization = civilizations.get(turn % civilizations.size());
    }

    public int getTurnNumber(){
        return turn;
    }
}
