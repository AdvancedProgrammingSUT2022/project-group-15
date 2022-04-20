package model;

import java.util.ArrayList;

public class Game {
    private static Game game;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;
    // TODO: 4/20/2022  map???


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
        // TODO: 4/20/2022 set map???
    }

    public Civilization getPlayingCivilization() {
        return civilizations.get(turn % civilizations.size());
    }

}
