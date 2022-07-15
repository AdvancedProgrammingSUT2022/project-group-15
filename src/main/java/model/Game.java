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

    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    private final ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;
    private int year = 4;
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

    public ArrayList<City> getOriginalCapitals() {
        return originalCapitals;
    }

    public static void startNewGame(ArrayList<User> users) {
        startNewGame(users, 10, 10, 0, 1);
    }

    public static void startNewGame(ArrayList<User> users, int length, int width, int roundPerSave, int keptSavedFiles) {
        game = new Game();
        game.map = new Map(width, length);
        // TODO: 7/10/2022 save????
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
        for (ArrayList<Hex> hexArrayList : map.map) {
            for (Hex hex : hexArrayList) {
                hex.setMovementPrice(hex.calculateMovementPrice());
            }
        }
        selectedCivilization.nextTurn();
        turn++;
        if (turn % civilizations.size() == 0)
            year += 6;
        if (year >= 2050)
            showWinner();
        selectedCivilization = civilizations.get(turn % civilizations.size());
    }

    private void showWinner() {
        if (year < 2050) {
            System.out.println("congratulations " + selectedCivilization.getUser().getUsername() + "!!! you won");
            return;
        }
        Civilization winner = civilizations.get(0);
        int bestScore = 0;
        for (Civilization civilization : civilizations) {
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
        System.out.println("congratulations " + winner.getUser().getUsername() + "!!! you won");
    }

    public int getTurnNumber() {
        return turn;
    }

    public int getAverageGold() {
        int ans = 0;
        for (Civilization civilization : civilizations) {
            ans += civilization.getGoldStorage();
        }
        return ans / civilizations.size();
    }

    public int getAverageCity() {
        int ans = 0;
        for (Civilization civilization : civilizations) {
            ans += civilization.getCities().size();
        }
        return ans / civilizations.size();
    }

    public int getAveragePopulation() {
        int ans = 0;
        for (Civilization civilization : civilizations) {
            ans += civilization.getPopulation();
        }
        return ans / civilizations.size();
    }

    public int getAverageUnit() {
        int ans = 0;
        for (Civilization civilization : civilizations) {
            ans += civilization.getUnits().size();
        }
        return ans / civilizations.size();
    }

    public int getAverageArea() {
        int result = 0;
        for (Civilization civilization : civilizations) {
            result += civilization.getArea();
        }
        return result / civilizations.size();
    }

    public int getBestGold() {
        int max = 0;
        for (Civilization civilization : civilizations) {
            if (civilization.getGoldStorage() >= max) {
                max = civilization.getGoldStorage();
            }
        }
        return max;
    }

    public int getBestUnit() {
        int max = 0;
        for (Civilization civilization : civilizations) {
            if (civilization.getUnits().size() >= max) {
                max = civilization.getUnits().size();
            }
        }
        return max;
    }

    public int getBestPopulation() {
        int max = 0;
        for (Civilization civilization : civilizations) {
            if (civilization.getPopulation() >= max) {
                max = civilization.getPopulation();
            }
        }
        return max;
    }

    public int getBestCity() {
        int max = 0;
        for (Civilization civilization : civilizations) {
            if (civilization.getCities().size() >= max) {
                max = civilization.getCities().size();
            }
        }
        return max;
    }

    public int getBestArea() {
        int max = 0;
        for (Civilization civilization : civilizations) {
            if (civilization.getArea() >= max) {
                max = civilization.getArea();
            }
        }
        return max;
    }
}
