package server.controller;


import server.enums.Avatar;
import server.model.User;


public class ProfileMenuController {

    public void logout() {
        User.setLoggedInUser(null);
    }

    public void deleteCurrentPlayerAccount() {
        User.deleteAccountOfLoggedInPlayer();
    }

    public boolean isStrong(String password) {
        return password.length() >= 6 && password.matches(".*\\w+.*") && password.matches(".*\\d+.*");
    }

    public String submitChanges(String nickname, String password) {
        if (nickname.length() != 0) {
            if (User.getUserByNickname(nickname) != null) {
                return "nickname exists!";
            }
            User.getLoggedInUser().setNickname(nickname);
        }
        if (password.length() != 0) {
            if (!isStrong(password)) {
                return "password is weak!";
            }
            User.getLoggedInUser().setPassword(password);
        }
        return "changes submitted";
    }

    public void changeAvatar(Double index) {
        User.getLoggedInUser().setAvatar(Avatar.values()[index.intValue()]);
    }
}
