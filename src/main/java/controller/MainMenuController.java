package controller;

import model.Civilization;
import model.Game;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuController {
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

    /**
     * log out from user's account
     *
     * @author Parsa
     */
    public void logout() {
        User.setLoggedInUser(null);
    }

    public boolean isGameStarted() {
        return Game.getGame() != null;
    }

    public User getLoggedInPlayer() {
        return User.getLoggedInUser();
    }
}
