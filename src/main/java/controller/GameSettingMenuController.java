package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSettingMenuController {
    public String inviteFriend(String username, VBox friendsInGame) {
        if (friendsInGame.getChildren().size() >= 6)
            return "lobby is full";
        if (User.getUserByUsername(username) == null)
            return "no user with this name";
        for (Node child : friendsInGame.getChildren()) {
            if (((Label) child).getText().equals(username))
                return "this user is already in lobby";
        }
        Label label = new Label(username);
        friendsInGame.getChildren().add(label);
        return "done";
    }

    public String findGame(ChoiceBox<Integer> numberOfPlayersBox) {
        if (numberOfPlayersBox.getValue() == null)
            return "error : select number of players";
        if (numberOfPlayersBox.getValue() > User.getUsers().size())
            return "error : we dont have " + numberOfPlayersBox.getValue() + " users";
        // TODO: 6/29/2022

        return "finding";
    }

    /**
     * create and start game
     *
     * @param usernames users for the game
     * @return message to be shown
     * @author amir and Parsa
     */
    public String startGame(HashMap<Integer, String> usernames, int length, int width, int roundPerSave, int keptSavedFiles) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            users.add(null);
        }

        for (Integer number : usernames.keySet()) {
            try {
                users.set(number - 1, User.getUserByUsername(usernames.get(number)));
            } catch (Exception e) {
                return Controller.addNotification(-1, "invalid player numbers");
            }
        }

        Game.startNewGame(users, length, width, roundPerSave, keptSavedFiles);
        return Controller.addNotification(-1, "a new game started with " + users.size() + " players");

    }

    public String gameWithFriends(VBox friendsInGame, int length, int width, int autoSave, int keptSavedFiles) {
        if (friendsInGame.getChildren().size() == 1)
            return "no one is selected";

        HashMap<Integer, String> hashMap = new HashMap<>();

        for (int i = 1; i <= friendsInGame.getChildren().size(); i++) {
            hashMap.put(i, ((Label) friendsInGame.getChildren().get(i - 1)).getText());
        }

        return startGame(hashMap, length, width, autoSave, keptSavedFiles);
    }

    public String loadSavedGame(String name) {
        String xml;
        try {
            xml = new String(Files.readAllBytes(Paths.get("./src/main/resources/savedGames/" + name)));
        } catch (IOException e) {
            return "file doesn't exists";
        }

        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        Game game = (Game) xStream.fromXML(xml);
        Game.setGame(game);
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            civilization.setUser(User.getUserByUsername(civilization.getUsername()));
        }
        return "game is loaded successfully";
    }
}
