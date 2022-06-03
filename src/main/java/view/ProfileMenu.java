package view;

import controller.ProfileMenuController;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu implements Initializable {
    private final ProfileMenuController controller = new ProfileMenuController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Scene getScene() {
        return null;
    }


//    @Override
//    protected String checkCommand(String command) {
//        Matcher matcher;
//        if (command.equals("menu show-current")) {
//            System.out.println("Profile Menu");
//        } else if (command.equals("menu exit")) {
//            System.out.println("you are in the main menu");
//            return "main menu";
//        } else if (command.startsWith("menu enter")) {
//            System.out.println("menu navigation is not possible");
//        } else if ((matcher = getMatcher(command, "^profile change (-n|--nickname) (?<nickname>\\S+)$")) != null) {
//            System.out.println(controller.changeNickname(matcher));
//        } else if (((matcher = getMatcher(command, "^profile change (-p|--password) (-c|--current) (?<currentPassword>\\S+) (-n|--new) (?<newPassword>\\S+)$")) != null) ||
//                ((matcher = getMatcher(command, "^profile change (-p|--password) (-n|--new) (?<newPassword>\\S+) (-c|--current) (?<currentPassword>\\S+)$")) != null)) {
//            System.out.println(controller.changePassword(matcher));
//        } else if (command.startsWith("profile change -u") ||
//                command.startsWith("profile change --username")) {
//            System.out.println("Sorry, Username is fixed");
//        } else {
//            System.out.println("invalid command!");
//        }
//        return "continue";
//    }
}
