import controller.Controller;
import controller.GameMenuController;
import model.Game;
import model.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        Game.getGame().startNewGame( new ArrayList<User>() );
//        GameMenuController gameMenuController = new GameMenuController();
//        gameMenuController.showMap();
        Controller controller = new Controller();
        controller.run();
    }
}
