package controller;

import model.User;

public class LoginMenuController {

    /**
     * creates a new user
     *
     * @return the message to be shown on the screen
     * @author Parsa
     */
    public String signUp(String username, String password, String nickname) {
        User.loadUsers();

        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            return Controller.addNotification(-1, "please fill the fields!");
        }

        if (User.getUserByUsername(username) != null) {
            return Controller.addNotification(-1, "user with username " + username + " already exists");
        }

        if (User.getUserByNickname(nickname) != null) {
            return Controller.addNotification(-1, "user with nickname " + nickname + " already exists");
        }

        if (!isStrong(password)) {
            return Controller.addNotification(-1, "password is weak!");
        }

        User.addUser(username, password, nickname);
        login(username, password);
        return Controller.addNotification(-1, "user created successfully!");
    }

    /**
     * log in user by getting username and password
     *
     * @return the message to be shown on the screen
     * @author Parsa
     */
    public String login(String username, String password) {
        User.loadUsers();
        if (username.isEmpty() || password.isEmpty()) {
            return Controller.addNotification(-1, "please fill the fields!");
        }

        if (User.getUserByUsername(username) == null || !User.getUserByUsername(username).getPassword().equals(password)) {
            return Controller.addNotification(-1, "Username and password didn't match!");
        }

        User.setLoggedInUser(User.getUserByUsername(username));
        return Controller.addNotification(-1, "user logged in successfully!");
    }

    /**
     * @return true if a user is logged in
     * @author Parsa
     */
    public boolean isUserLoggedIn() {
        return User.getLoggedInUser() != null;
    }

    public boolean isStrong(String password) {
        return password.length() >= 6 && password.matches(".*\\w+.*") && password.matches(".*\\d+.*");
    }
}
