package server.controller;



import server.enums.Avatar;
import server.model.User;


public class ProfileMenuController {
    User user;
    public ProfileMenuController(User user) {
        this.user = user;
    }

    public void logout() {
        ;
    }

    public void deleteCurrentPlayerAccount(User user) {
        User.deleteAccountOfLoggedInPlayer(user);
    }

    public boolean isStrong(String password) {
        return password.length() >= 6 && password.matches(".*\\w+.*") && password.matches(".*\\d+.*");
    }

    public String submitChanges(String nickname, String password) {
        if (nickname.length() != 0) {
            if (User.getUserByNickname(nickname) != null) {
                return "nickname exists!";
            }
            user.setNickname(nickname);
        }
        if (password.length() != 0) {
            if (!isStrong(password)) {
                return "password is weak!";
            }
            user.setPassword(password);
        }
        return "changes submitted";
    }

    public void changeAvatar(Double index) {
        user.setAvatar(Avatar.values()[index.intValue()]);
    }
}
