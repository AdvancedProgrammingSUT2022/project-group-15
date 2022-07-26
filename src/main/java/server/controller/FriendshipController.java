package server.controller;

import server.model.User;

public class FriendshipController {

    public String sendReq(String username, String name) {
        System.out.println("find");
        if (User.getUserByUsername(username) == null) {
            return "not found";
        } else {
            User.getUserByUsername(username).getFriendReqs().add(name);
            return "friend req sent";
        }
    }
}
