import controller.Controller;
import controller.GameMenuController;
import enums.UnitName;
import model.Game;
import model.User;
import model.unit.WorkerUnit;
import view.GameMenu;

import java.util.ArrayList;
import java.util.SortedMap;

public class Main {
    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","","0"));
        users.add(new User("","","1"));

        Game.startNewGame(users);
        GameMenu gameMenu = new GameMenu();
        gameMenu.run();

        Controller controller = new Controller();
        controller.run();
    }
}




















//        GameMenuController gameMenuController = new GameMenuController();
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User("","","0"));
//        users.add(new User("","","1"));
//        users.add(new User("","","2"));
//        users.add(new User("","","3"));
//        Game.startNewGame(users);
//        System.out.println(Game.getGame().getSelectedCivilization().getUnits().get(0).getCoordinatesInMap().get('x')+ " "+
//                Game.getGame().getSelectedCivilization().getUnits().get(0).getCoordinatesInMap().get('y'));
//        System.out.println(gameMenuController.showMapOnPosition(3,3));
//        gameMenuController.showMap();
//        GameMenuController controller2 = new GameMenuController();
//        System.out.println(controller2.showTechnologyInfo());
