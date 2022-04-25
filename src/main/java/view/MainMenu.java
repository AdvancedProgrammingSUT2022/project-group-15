package view;

import controller.GameMenuController;
import controller.MainMenuController;
import model.Game;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends Menu {
    private final MainMenuController controller = new MainMenuController();

    /**
     * @author Parsa
     */
    @Override
    protected String checkCommand(String command) {
        Matcher matcher;
        if (command.equals("menu show-current")) {
            System.out.println("Main Menu");
        } else if (command.equals("menu exit") || command.equals("user logout")) {
            if (command.equals("user logout")) {
                controller.logout();
                System.out.println("user logged out successfully");
            }
            System.out.println("you are in the login menu");
            return "logout";
        } else if (command.equals("menu enter profile menu")) {
            System.out.println("you are in the profile menu");
            return "profile menu";
        } else if (command.equals("menu enter game menu")) {
            if (controller.isGameStarted()) {
                return "game menu";
            }
            System.out.println("please start a new game first");
        } else if (command.startsWith("play game ")) {
            HashMap<Integer, String> usernames = new HashMap<>();
            matcher = Pattern.compile("(?:-p|--player)(?<number>\\d) (?<username>\\S+)").matcher(command);
            while (matcher.find()) {
                usernames.put(Integer.parseInt(matcher.group("number")), matcher.group("username"));
            }
            if (usernames.size() <= 1) {
                System.out.println("not enough players");
            } else {
                System.out.println(controller.startGame(usernames));
                System.out.println("you are in the game menu");
                return "game menu";
            }
        } else {
            System.out.println("invalid command!");
        }
        return "continue";
    }
}
/*user login -u erfan -p 123erfan
menu enter game menu
show map 20 20
 */