package model;

import java.util.ArrayList;

public class Game {
    private static Game game;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
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

    public void startNewGame(ArrayList<User> users) {
        for (User user : users) {
            civilizations.add(new Civilization(user));
        }
    }

    public Civilization getPlayingCivilization() {
        return civilizations.get(turn % civilizations.size());
    }

}
