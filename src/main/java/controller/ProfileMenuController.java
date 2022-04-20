package controller;

import model.User;

import java.util.regex.Matcher;

public class ProfileMenuController {
    /**
     * check for unique nickname then change
     *
     * @return the message of he state
     * @author erfan
     */
    public String changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");
        if (User.getUserByNickname(nickname) != null) {
            return "user with nickname " + nickname + " already exists";
        }
        //return alaki
        return "c";
    }
}
