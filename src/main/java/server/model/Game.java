package server.model;

import com.thoughtworks.xstream.XStream;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Game {
    private static Game game;

    private final ArrayList<String> notificationHistory = new ArrayList<>();
    private final ArrayList<Civilization> civilizations = new ArrayList<>();
    private final ArrayList<City> originalCapitals = new ArrayList<>();
    private int turn;
    private int year;
    private int roundPerSave;
    private int keptSavedFiles;
    private long idForSaving;
    private Civilization selectedCivilization;
    public Map map;

    public int getYear() {
        return year;
    }

    public int getTurn() {
        return turn;
    }

    private Game() {
        turn = 0;
        year = 4;
        idForSaving = System.currentTimeMillis();
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        Game.game = game;
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
        // System.out.println(Game.getGame().getRows());
        if (roundPerSave <=0 )
            roundPerSave = Integer.MAX_VALUE;
        game.roundPerSave = roundPerSave;
        game.keptSavedFiles = keptSavedFiles;
        game.map.fillMap();
        for (User user : users) {
            game.civilizations.add(new Civilization(user));
        }
        for (Civilization civilization : game.civilizations) {
            civilization.setUp();
        }
        game.selectedCivilization = game.civilizations.get(0);
    }

    public String saveGame() {

        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/savedGames/" + idForSaving + "_" + turn + ".xml");
            XStream xStream = new XStream();
            fileWriter.write(xStream.toXML(this));
            fileWriter.close();
            return "saved with name : " + idForSaving + "_" + turn;

        } catch (IOException e) {
            e.printStackTrace();
            return "an error occurred while saving";
        }

    }

    public int getRows() {
        return map.getRowsNumber();
    }

    public int getColumns() {
        return map.getColumnsNumber();
    }


    public void nextTurn() {
        if ( (turn+1) % roundPerSave==0){
            saveGame();
            DeleteExtraSaved();
        }
        for (ArrayList<Hex> hexArrayList : map.map) {
            for (Hex hex : hexArrayList) {
                hex.setMovementPrice(hex.calculateMovementPrice());
            }
        }
        selectedCivilization.nextTurn();
        turn++;
        if (turn % civilizations.size() == 0)
            year += 6;

        selectedCivilization = civilizations.get(turn % civilizations.size());
    }

    private void DeleteExtraSaved() {
        if (turn+1 > keptSavedFiles * roundPerSave ){
            deleteSavedFile(idForSaving + "_" + (turn- keptSavedFiles * roundPerSave));
        }
    }

    private void deleteSavedFile(String name) {

        Path path = FileSystems.getDefault().getPath("./src/main/resources/savedGames/" + name + ".xml");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public boolean selectedHasAllCapitals() {
        if (originalCapitals.size() <= 1)
            return false;
        for (City city : originalCapitals) {
            if (city.getOwner() != selectedCivilization)
                return false;
        }
        return true;
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

    public void setYear(int year) {
        this.year = year;
    }

    public String addNotification(int turnNumber, String message) {
        String notif = "";
        if (turnNumber == -1) {
            notif += "<< Not in the game menu >> : ";
        } else {
            notif += "<< Turn " + turnNumber + " >> : ";
        }
        notif += message;
        notificationHistory.add(notif);
        return message;
    }

    public ArrayList<String> getNotificationHistory() {
        return notificationHistory;
    }
}
