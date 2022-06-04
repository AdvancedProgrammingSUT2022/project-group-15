package controller;

import javafx.collections.ObservableList;
import model.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardController {

    public void loadSortedPlayers(ObservableList<User> rawList) {
        ArrayList<User> players = User.getUsers();
        Collections.sort(players);
        rawList.addAll(players);
    }
}
