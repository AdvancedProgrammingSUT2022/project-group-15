package model;

import enums.HexVisibility;

import java.util.ArrayList;

public class Game {
    private static Game game = null;

    private ArrayList<Civilization> civilizations = new ArrayList<>();
    private ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;

    private int xMap = 20;
    private int yMap = 20;
    public ArrayList<ArrayList<Hex>> map;
 /*
    structure of map:
              y=0      y=1       y=2      y=3
    x=0      [0][0]   [0][1]   [0][2]   [0][3]

    x=1          [1][0]   [1][1]   [1][2]   [0][3]

    x=2      [2][0]   [2][1]   [2][2]   [0][3]

    x=3          [3][0]   [3][1]   [3][2]   [0][3]
*/

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

        createMap();

    }


    public Civilization getPlayingCivilization() {
        return civilizations.get(turn % civilizations.size());
    }
    public int getRows(){
        return xMap;
    }
    public int getColumns(){
        return yMap;
    }

    private void createMap() {
        map = new ArrayList<>();
        for (int i = 0; i < xMap; i++) {
            ArrayList<Hex> row = new ArrayList<>();
            for (int j = 0; j < yMap; j++) {
                Hex hex = createRandomHex(i, j);
                row.add(hex);
            }
            map.add(row);
        }
    }

    private Hex createRandomHex(int x, int y) {
        return new Hex();
    }

}
