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

        if (User.loggedInUser.getNickname().equals(nickname)) {
            return "please enter a new nickname";
        }

        if (User.getUserByNickname(nickname) != null) {
            return "user with nickname " + nickname + " already exists";
        }

        User.loggedInUser.setNickname(nickname);
        return "nickname changed successfully!";
    }

    /**
     * changes the password of loggedInUser
     * @return the message to be shown
     * @author Parsa
     */
    public String changePassword(Matcher matcher) {
        String currentPassword = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");

        if(!User.loggedInUser.getPassword().equals(currentPassword)){
            return "current password is invalid";
        }

        if(currentPassword.equals(newPassword)){
            return "please enter a new password";
        }

        User.loggedInUser.setPassword(newPassword);
        return "password changed successfully!";
    }
}
