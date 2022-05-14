package controller;

import view.*;

import java.util.ArrayList;

public class Controller {
    private final LoginMenu loginMenu= new LoginMenu();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private final GameMenu gameMenu = new GameMenu();
    private static final ArrayList<String> notificationHistory = new ArrayList<>();

    /**
     * controls the main flow of program (moving between menus)
     * @author Parsa
     */
    public void run() {
        if (loginMenu.run().equals("exit")) return;

        while (true) {
            switch (mainMenu.run()) {
                case "game menu":
                    gameMenu.run();
                    break;
                case "profile menu":
                    profileMenu.run();
                    break;
                case "logout":
                    if (loginMenu.run().equals("exit")) return;
                    break;
            }
        }
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
