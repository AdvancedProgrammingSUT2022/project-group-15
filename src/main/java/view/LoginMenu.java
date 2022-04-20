package view;

import controller.LoginMenuController;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    protected String checkCommand(String command) {
        Matcher matcher;
        if (command.equals("menu show-current")) {
            System.out.println("Login Menu");
        } else if (command.equals("menu exit")) {
            System.out.println("Program Closed");
            return "exit";
        } else if (command.equals("menu enter main menu") || command.equals("menu enter profile menu") || command.equals("menu enter game menu")) {
            System.out.println("please login first");
        } else if (((matcher = getMatcher(command, "^user create (-u|--username) (?<username>\\S+) (-p|--password) (?<password>\\S+) (-n|--nickname) (?<nickname>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user create (-u|--username) (?<username>\\S+) (-n|--nickname) (?<nickname>\\S+) (-p|--password) (?<password>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user create (-p|--password) (?<password>\\S+) (-u|--username) (?<username>\\S+) (-n|--nickname) (?<nickname>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user create (-p|--password) (?<password>\\S+) (-n|--nickname) (?<nickname>\\S+) (-u|--username) (?<username>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user create (-n|--nickname) (?<nickname>\\S+) (-p|--password) (?<password>\\S+) (-u|--username) (?<username>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user create (-n|--nickname) (?<nickname>\\S+) (-u|--username) (?<username>\\S+) (-p|--password) (?<password>\\S+$")) != null)) {
            System.out.println(controller.createUser(matcher));
        } else if (((matcher = getMatcher(command, "^user login (-u|--username) (?<username>\\S+) (-p|--password) (?<password>\\S+)$")) != null) ||
                ((matcher = getMatcher(command, "^user login (-p|--password) (?<password>\\S+) (-u|--username) (?<username>\\S+)$")) != null)) {
            System.out.println(controller.login(matcher));
            return "main menu";
        } else {
            System.out.println("invalid command!");
        }


        return "continue";
    }
}
