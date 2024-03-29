package server.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.scene.control.ChoiceBox;

import server.model.Civilization;
import server.model.Game;
import server.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSettingMenuController {
    User user;

    public GameSettingMenuController(User user) {
        this.user = user;
    }

    public String inviteFriend(String username, ArrayList<String> friendsInGame) {
        if (friendsInGame.size() >= 6)
            return "lobby is full";
        if (User.getUserByUsername(username) == null)
            return "no user with this name";
        for (String s : friendsInGame) {
            if (s.equals(username))
                return "this user is already in lobby";
        }
        if (username.equals(user.getUsername()))
            return "done";


        for (SocketHandler socketHandler : ServerController.getInstance().getSocketHandlers()) {
            System.out.println("number of user : "+ServerController.getInstance().getSocketHandlers().size() );
            if (socketHandler.getUser()==null)
                continue;
            if (socketHandler.getUser().getUsername().equals(username)) {

                socketHandler.sendCommand("invite from " + user.getUsername());
                return "wait for response from other player";
            }
        }

        return "player is not online";
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
                return Game.getGame().addNotification(-1, "invalid player numbers");
            }
        }

        Game.startNewGame(users, length, width, roundPerSave, keptSavedFiles);
        return Game.getGame().addNotification(-1, "a new game started with " + users.size() + " players");

    }

    public String gameWithFriends(ArrayList<String> friendsInGame, Double length, Double width, Double autoSave, Double keptSavedFiles) {
        if (friendsInGame.size() == 1)
            return "no one is selected";

        HashMap<Integer, String> hashMap = new HashMap<>();

        for (int i = 1; i <= friendsInGame.size(); i++) {
            hashMap.put(i, friendsInGame.get(i - 1));
        }

        return startGame(hashMap, length.intValue(), width.intValue(), autoSave.intValue(), keptSavedFiles.intValue());
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
