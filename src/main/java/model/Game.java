package model;

import controller.GameMenuController;
import enums.Feature;
import enums.NeighborHex;
import enums.Resource;
import enums.Terrain;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Game {
    private static Game game = new Game();

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;
    private Civilization selectedCivilization;
    public Map map ;


    private Game() {
        turn = 0;
        selectedCivilization = civilizations.get(0);
    }

    public static Game getGame() {
        return game;
    }

    public Civilization getSelectedCivilization() {
        return selectedCivilization;
    }

    public static void startNewGame(ArrayList<User> users) {
        game = new Game();
        game.map = new Map(8 ,8 );//this constants might change later or be given by user
        //System.out.println(Game.getGame().getRows());
        game.map.fillMap();
        for (User user : users) {
            game.civilizations.add(new Civilization(user));
        }
        for (Civilization civilization : game.civilizations) {
            civilization.setUp();
        }
    }

    public Civilization getPlayingCivilization() {
        return civilizations.get(turn % civilizations.size());
    }

    public int getRows() {
        return map.getRowsNumber();
    }

    public int getColumns() {
        return map.getColumnsNumber();
    }





    public void nextTurn() {
        turn++;
        selectedCivilization = civilizations.get(turn % civilizations.size());
    }
}
