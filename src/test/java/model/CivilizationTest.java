package model;

import controller.GameMenuController;
import junit.framework.TestCase;

import java.util.ArrayList;

public class CivilizationTest extends TestCase {

    public void testAdjustVisibility() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","","0", 0));
        users.add(new User("","","1", 0));
        users.add(new User("","","2", 0));
        users.add(new User("","","3", 0));
        Game.startNewGame(users);

    }

    public void testShowCityOnMap (){
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","","0", 0));
        users.add(new User("","","1", 0));
        users.add(new User("","","2", 0));
        users.add(new User("","","3", 0));
        Game.startNewGame(users);
        Game.getGame().getSelectedCivilization().getCities().add(new City("test", 2,2,null));
        System.out.println(gameMenuController.showMapOnPosition(2,2));
    }



}