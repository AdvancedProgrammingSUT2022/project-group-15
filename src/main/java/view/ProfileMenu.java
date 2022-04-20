package view;

import controller.ProfileMenuController;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    private ProfileMenuController controller = new ProfileMenuController();

    @Override
    protected String checkCommand(String command) {
        Matcher matcher;
        if (command.equals("menu show-current")) {
            System.out.println("Profile Menu");
        } else if (((matcher = getMatcher(command, "^profile change (-n|--nickname) (?<nickname>\\S+)$")) != null)) {
            System.out.println(controller.changeNickname(matcher));
        }
        //return alaki
        return "s";
    }
}
