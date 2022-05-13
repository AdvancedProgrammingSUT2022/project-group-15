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
            return Controller.addNotification(-1,"please enter a new nickname");
        }

        if (User.getUserByNickname(nickname) != null) {
            return Controller.addNotification(-1,"user with nickname " + nickname + " already exists");
        }

        User.getLoggedInUser().setNickname(nickname);
        User.saveUsers();
        return Controller.addNotification(-1,"nickname changed successfully!");
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
            return Controller.addNotification(-1,"current password is invalid");
        }

        if (currentPassword.equals(newPassword)) {
            return Controller.addNotification(-1,"please enter a new password");
        }

        User.getLoggedInUser().setPassword(newPassword);
        User.saveUsers();
        return Controller.addNotification(-1,"password changed successfully!");
    }
}
