package controller;

import model.User;

import java.util.regex.Matcher;

public class ProfileMenuController {
    /**
     * change the nickname of loggedInUser
     * @return the message to be shown
     * @author Parsa
     */
    public String changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");

        if (User.getLoggedInUser().getNickname().equals(nickname)) {
            return "please enter a new nickname";
        }

        if (User.getUserByNickname(nickname) != null) {
            return "user with nickname " + nickname + " already exists";
        }

        User.getLoggedInUser().setNickname(nickname);
        User.saveUsers();
        return "nickname changed successfully!";
    }

    /**
     * changes the password of loggedInUser
     *
     * @return the message to be shown
     * @author Parsa
     */
    public String changePassword(Matcher matcher) {
        String currentPassword = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");

        if (!User.getLoggedInUser().getPassword().equals(currentPassword)) {
            return "current password is invalid";
        }

        if (currentPassword.equals(newPassword)) {
            return "please enter a new password";
        }

        User.getLoggedInUser().setPassword(newPassword);
        User.saveUsers();
        return "password changed successfully!";
    }
}
