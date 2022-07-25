package client.controller;

import client.model.Request;
import client.model.Response;
import client.view.*;
import javafx.stage.Stage;
import server.model.GlobalThings;

import java.util.ArrayList;

public class Controller {
    private static Stage window = null;
    private static final LoginMenu loginMenu = new LoginMenu();
    private static final MainMenu mainMenu = new MainMenu();
    private static final ProfileMenu profileMenu = new ProfileMenu();
    private static GameMenu gameMenu = new GameMenu();
    private static GameSettingsMenu gameSettingsMenu = new GameSettingsMenu();
    private static final ScoreBoard scoreboard = new ScoreBoard();
    private static final TechnologyTree technologyTree = new TechnologyTree();


    private static final SocketController SOCKET_CONTROLLER = new SocketController();


    public static ProfileMenu getProfileMenu() {
        return profileMenu;
    }

    public static ScoreBoard getScoreBoard() {
        return scoreboard;
    }

    public static Object send(String method, Object... parameters) {
        Request request = new Request();
        request.setMethodName(method);
        for (Object parameter : parameters) {
            request.addParameter(parameter);
        }
        Response response = SOCKET_CONTROLLER.send(request);
        return response.getAnswer();
    }

    public void run(Stage primaryStage) {
        window = primaryStage;
        window.setResizable(false);
        window.setScene(loginMenu.getScene());
        window.setTitle("Civilization");
        window.getIcons().add(GlobalThings.CIVILIZATION_IMAGE);
        window.show();
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    public static LoginMenu getLoginMenu() {
        return loginMenu;
    }

    public static GameMenu getGameMenu() {
        return gameMenu;
    }

    public static void setGameMenu(GameMenu gameMenu) {
        Controller.gameMenu = gameMenu;
    }

    public static GameSettingsMenu getGameSettingsMenu() {
        return gameSettingsMenu;
    }

    public static void setGameSettingsMenu(GameSettingsMenu gameSettingsMenu) {
        Controller.gameSettingsMenu = gameSettingsMenu;
    }

    public static TechnologyTree getTechnologyTree() {
        return technologyTree;
    }

    public static Stage getWindow() {
        return window;
    }

    /**
     * add a message to the notification history
     *
     * @param turnNumber turn number to be mentioned at the beginning of notification
     * @param message    the body of notification
     * @return the message
     * @author Parsa
     */


}