package server.controller;

import client.controller.Controller;
import server.model.User;

import java.util.ArrayList;

public class FriendshipController {

    public String sendReq(String username, String name) {
        if (User.getUserByUsername(username) == null) {
            return "not found";
        } else {
            if (username.equals(name)) {
                return "another";
            } else if (User.getUserByUsername(username).getFriendReqs().contains(name)) {
                return "already sent";
            } else {
                User.getUserByUsername(username).getFriendReqs().add(name);
                return "friend req sent" + "\n" + "username : " + User.getUserByUsername(username).getUsername() + "\n" + "nickname : " + User.getUserByUsername(username).getNickname();
            }
        }
    }

    public ArrayList<String> allReqs(String name) {
        return User.getUserByUsername(name).getFriendReqs();
    }

    public void delete(String name) {
        User.getUserByUsername(User.getUserByUsername(name).getFriendReqs().get(0)).getFriends().add(name);
        User.getUserByUsername(name).getFriends().add(User.getUserByUsername(name).getFriendReqs().get(0));
        User.getUserByUsername(name).getFriendReqs().remove(0);
    }

    public void deleteAgain(String name) {
        System.out.println("sallllllam");
        User.getUserByUsername(name).getFriendReqs().remove(0);
    }

    public ArrayList<String> friend(String name) {
        System.out.println("salam");
        return User.getUserByUsername(name).getFriends();
    }
}
