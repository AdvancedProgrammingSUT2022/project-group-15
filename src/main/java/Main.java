import controller.Controller;
import controller.GameSettingMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.User;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
//        GameSettingMenuController gameSettingMenuController = new GameSettingMenuController();
//        System.out.println(gameSettingMenuController.loadSavedGame("1657914508556","0"));
//        System.out.println(Game.getGame().map);
//        Game.getGame().nextTurn();
//        System.out.println(Game.getGame().getTurnNumber());
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.run(primaryStage);
    }
}