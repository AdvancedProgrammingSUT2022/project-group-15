package controller;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import model.Game;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GameSettingMenuController {
    public String inviteFriend(String username, VBox friendsInGame) {
        if (friendsInGame.getChildren().size()>=10)
            return "lobby is full";
        if (User.getUserByUsername(username)==null)
            return "no user with this name";
        for (Node child : friendsInGame.getChildren()) {
            if (((Label)child).getText().equals(username))
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
    public String startGame(HashMap<Integer, String> usernames) {
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

        Game.startNewGame(users);
        return Controller.addNotification(-1, "a new game started with " + users.size() + " players");
    }

    public String gameWithFriends(VBox friendsInGame) {
        if (friendsInGame.getChildren().size() == 2)
            return "no one is selected";

        HashMap<Integer, String> hashMap = new HashMap<>();

        for (int i = 1; i <= friendsInGame.getChildren().size()-1 ; i++) {
            hashMap.put(i, ((Label) friendsInGame.getChildren().get(i)).getText());
        }

        return startGame(hashMap);
    }
}
