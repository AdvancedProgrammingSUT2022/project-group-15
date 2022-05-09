package model.unit;

import enums.UnitName;
import junit.framework.TestCase;
import model.Game;
import model.User;

import java.util.ArrayList;

public class UnitTest extends TestCase {

    public void testUnitCanAttack() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", ""));
        Game.startNewGame(users);
        Unit unit = new RangedMilitary(5, 5, Game.getGame().getSelectedCivilization(), UnitName.ARTILLERY);
        assertTrue(unit.unitCanAttack(3, 3));
    }

    public void testUnitCanNotAttack() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", ""));
        Game.startNewGame(users);
        Unit unit = new RangedMilitary(5, 5, Game.getGame().getSelectedCivilization(), UnitName.ARTILLERY);
        assertFalse(unit.unitCanAttack(3, 2));
    }
}