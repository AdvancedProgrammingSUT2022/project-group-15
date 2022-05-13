package controller;

import enums.Feature;
import enums.Resource;
import enums.Terrain;
import enums.UnitName;
import model.Game;
import model.Hex;
import model.User;
import model.unit.WorkerUnit;
import org.junit.Test;
import view.GameMenu;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameMenuControllerTest {


    @Test
    public void moveTest() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "0"));
        users.add(new User("", "", "1"));
        users.add(new User("", "", "2"));
        users.add(new User("", "", "3"));
        Game.startNewGame(users);
        WorkerUnit workerUnit = new WorkerUnit(4, 4, Game.getGame().getSelectedCivilization(), UnitName.WORKER);

        gameMenuController.selectCivilUnit(4, 4);

        gameMenuController.moveSelectedUnitTo(4, 5);


        assertEquals(workerUnit, Game.getGame().map.map.get(4).get(5).getCivilUnit());

    }

    @Test
    public void moveNotPossibleTest() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "0"));
        users.add(new User("", "", "1"));
        users.add(new User("", "", "2"));
        users.add(new User("", "", "3"));
        Game.startNewGame(users);
        WorkerUnit workerUnit = new WorkerUnit(4, 4, Game.getGame().getSelectedCivilization(), UnitName.WORKER);

        gameMenuController.selectCivilUnit(4, 4);
        Game.getGame().map.map.get(0).set(0, new Hex(Terrain.OCEAN, Feature.NULL, Resource.NULL, false, 0, 0));


        assertEquals("cant go to destination (mountain or ice or sea) or blocked by other units", gameMenuController.moveSelectedUnitTo(0, 0));

    }


    public void seeMapWithSomeChanges() {
        GameMenuController gameMenuController = new GameMenuController();
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "0"));
        users.add(new User("", "", "1"));
        users.add(new User("", "", "2"));
        users.add(new User("", "", "3"));
        Game.startNewGame(users);
        GameMenu gameMenu = new GameMenu();
        gameMenu.run();

    }

}