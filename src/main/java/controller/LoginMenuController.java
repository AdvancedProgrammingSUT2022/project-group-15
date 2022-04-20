package controller;

import model.User;

import java.util.regex.Matcher;

public class LoginMenuController{

    /**
     * creates a new user
     * @return the message to be shown on the screen
     * @author Parsa
     */
    public String createUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String nickname = matcher.group("nickname");

        if(User.getUserByUsername(username) != null){
            return "user with username " + username + " already exists";
        }

        if(User.getUserByNickname(nickname) != null){
            return "user with nickname " + nickname + " already exists";
        }

        User.addUser(username, password, nickname);
        return "user created successfully!";
    }

    /**
     * log in user by getting username and password
     * @return the message to be shown on the screen
     * @author Parsa
     */
    public String login(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");

        if(User.getUserByUsername(username) == null || !User.getUserByUsername(username).getPassword().equals(password)){
            return "Username and password didn't match!";
        }
        User.loggedInUser = User.getUserByUsername(username);
        return "user logged in successfully!";
    }
}
