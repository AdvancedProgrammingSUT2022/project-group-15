import controller.Controller;
import controller.GameMenuController;
import model.Game;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GameMenuController gameMenuController = new GameMenuController();
        Game.startNewGame(new ArrayList<>());
        gameMenuController.showMap();
        Controller controller = new Controller();
        controller.run();
    }
}
