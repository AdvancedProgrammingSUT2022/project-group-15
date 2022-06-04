package controller;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.*;

import java.util.ArrayList;

public class Controller{
    private static Stage window = null;
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static final GameMenu gameMenu = new GameMenu();
    private static final ScoreBoard scoreboard = new ScoreBoard();
    private static final ArrayList<String> notificationHistory = new ArrayList<>();

    public static ProfileMenu getProfileMenu() {
        return profileMenu;
    }

    public static ScoreBoard getScoreBoard() {
        return scoreboard;
    }

    public void run(Stage primaryStage) {
        window = primaryStage;
        window.setResizable(false);
        window.setScene(loginMenu.getScene());
        window.setTitle("Civilization");
        window.getIcons().add(new Image(getClass().getResource("/icons/Civ-5-icon.png").toExternalForm()));
        window.show();
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }
    public static LoginMenu getLoginMenu() {
        return loginMenu;
    }
    public static Stage getWindow() {
        return window;
    }

    /**
     * add a message to the notification history
     * @param turnNumber turn number to be mentioned at the beginning of notification
     * @param message the body of notification
     * @return the message
     * @author Parsa
     */
    public static String addNotification(int turnNumber, String message){
        String notif = "";
        if(turnNumber == -1){
            notif += "<< Not in the game menu >> : ";
        } else {
            notif += "<< Turn " + turnNumber + " >> : ";
        }
        notif += message;
        notificationHistory.add(notif);
        return message;
    }

    public static ArrayList<String> getNotificationHistory() {
        return notificationHistory;
    }
}