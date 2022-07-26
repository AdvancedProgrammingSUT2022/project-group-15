package server.controller;

import server.model.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardController {

    public ArrayList<User> loadSortedPlayers() {
        ArrayList<User> players = new ArrayList<>(User.getUsers());
        Collections.sort(players);
        return players;
    }
}
