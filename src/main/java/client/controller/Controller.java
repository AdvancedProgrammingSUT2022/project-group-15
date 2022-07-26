package client.controller;


import client.model.GlobalThings;
import client.model.Request;
import client.model.Response;
import client.model.User;
import client.view.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.stage.Stage;
import server.model.Game;
import server.model.Hex;
import server.model.unit.Unit;


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

    public static User getUser(String username) {
        Request request = new Request();
        request.setMethodName("getUser");
        request.addParameter(username);
        Response response = SOCKET_CONTROLLER.send(request);
        return User.fromJson((String) response.getAnswer());
    }

    public static User getMyUser() {
        Request request = new Request();
        request.setMethodName("getMyUser");
        Response response = SOCKET_CONTROLLER.send(request);
        return User.fromJson((String) response.getAnswer());
    }
//
//    public static void updateUser() {
//        User.setLoggedInUser(getUser(User.getLoggedInUser().getUsername()));
//    }

    public static Game getGame(){
        Request request= new Request();
        request.setMethodName("getGame");
        Response response = SOCKET_CONTROLLER.send(request);
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        return (Game) xStream.fromXML((String) response.getAnswer());
    }

    public static Hex getHex(int x, int y){
        Request request= new Request();
        request.setMethodName("getHex");
        request.addParameter(x);
        request.addParameter(y);
        Response response = SOCKET_CONTROLLER.send(request);
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        return (Hex) xStream.fromXML(((String) response.getAnswer()).substring(26));
    }
    public static Unit getSelectedUnit(){
        Request request= new Request();
        request.setMethodName("getSelectedUnit");
        Response response = SOCKET_CONTROLLER.send(request);
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        return (Unit) xStream.fromXML((String) response.getAnswer());
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
}