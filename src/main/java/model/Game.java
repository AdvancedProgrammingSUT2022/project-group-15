package model;

import java.util.ArrayList;

public class Game {
    private static Game game;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    //city???
    private int turn;
    //map???


    private Game() {
        turn = 0;
    }


    public static Game getGame() {
        if (game == null)
            game = new Game();
        return game;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

}
