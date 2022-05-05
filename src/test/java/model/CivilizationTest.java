package model;

import controller.GameMenuController;
import junit.framework.TestCase;

import java.util.ArrayList;

public class CivilizationTest extends TestCase {

    public void testAdjustVisibility() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("","","0"));
        users.add(new User("","","1"));
        users.add(new User("","","2"));
        users.add(new User("","","3"));
        Game.startNewGame(users);

    }
}