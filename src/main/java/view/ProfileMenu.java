package view;

import controller.ProfileMenuController;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    private final ProfileMenuController controller = new ProfileMenuController();

    @Override
    protected String checkCommand(String command) {
        Matcher matcher;
        if (command.equals("menu show-current")) {
            System.out.println("Profile Menu");
        } else if (command.equals("menu exit")) {
            System.out.println("you are in the main menu");
            return "main menu";
        } else if (command.startsWith("menu enter")) {
            System.out.println("menu navigation is not possible");
        } else if ((matcher = getMatcher(command, "^profile change (-n|--nickname) (?<nickname>\\S+)$")) != null) {
            System.out.println(controller.changeNickname(matcher));
        } else if (((matcher = getMatcher(command, "^profile change (-p|--password) (-c|--current) (?<currentPassword>\\S+) (-n|--new) (?<newPassword>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^profile change (-p|--password) (-n|--new) (?<newPassword>\\S+)) (-c|--current) (?<currentPassword>\\S+)$")) != null)) {
            System.out.println(controller.changePassword(matcher));
        } else {
            System.out.println("invalid command!");
        }
        return "continue";
    }
}
