package server.controller;

import server.model.User;

public class ScoreBoardController {

    public int getNumberOfUsers() {
        return User.getUsers().size();
    }
}
