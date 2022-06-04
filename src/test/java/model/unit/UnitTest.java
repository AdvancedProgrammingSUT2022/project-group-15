package model.unit;

import enums.Feature;
import enums.Resource;
import enums.Terrain;
import enums.UnitName;
import junit.framework.TestCase;
import model.Civilization;
import model.Game;
import model.Hex;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTest extends TestCase {

    public void testUnitCanAttack() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "", 0));
        Game.startNewGame(users);
        Unit unit = new RangedMilitary(5, 5, Game.getGame().getSelectedCivilization(), UnitName.ARTILLERY);
        assertTrue(unit.unitCanAttack(3, 3));
    }

    public void testUnitCanNotAttack() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "", 0));
        Game.startNewGame(users);
        Unit unit = new RangedMilitary(5, 5, Game.getGame().getSelectedCivilization(), UnitName.ARTILLERY);
        assertFalse(unit.unitCanAttack(3, 2));
    }

    public void testFindShortestPathByDijkstra() {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "", 0));
        Game.startNewGame(users);


        Civilization mockedCivilization = mock(Civilization.class);
        MeleeMilitary meleeMilitary = new MeleeMilitary(0, 0, mockedCivilization, UnitName.ANTITANKGUN);

        Hex hex = mock(Hex.class);
        Game.getGame().map.map.get(1).set(0, hex);
        when(hex.getMovementPrice()).thenReturn((double) 0);
        Assert.assertEquals( 0, hex.getMovementPrice(),0.1);
        double dist = meleeMilitary.findShortestPathByDijkstra(1, 0);
        Assert.assertEquals(0, dist,0.1);
    }

    public void testMoveToMountain() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "", 0));
        Game.startNewGame(users);

        Civilization mockedCivilization = mock(Civilization.class);
        MeleeMilitary meleeMilitary = new MeleeMilitary(5, 5, mockedCivilization, UnitName.ANTITANKGUN);
        Game.getGame().map.map.get(5).set(6, new Hex(Terrain.MOUNTAIN, Feature.NULL, Resource.NULL, false, 5, 6));
        double dist = meleeMilitary.findShortestPathByDijkstra(5, 6);
        assertTrue(dist > 100000);
    }

    public void testZOCWorking(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", "1", 0));
        users.add(new User("", "", "2", 0));
        Game.startNewGame(users);

        MeleeMilitary meleeMilitary = new MeleeMilitary(5, 5, Game.getGame().getSelectedCivilization(), UnitName.ANTITANKGUN);
        Game.getGame().map.map.get(5).set(6,new Hex(Terrain.PLAIN,Feature.NULL,Resource.NULL,false,5,6));
        RangedMilitary enemy = new RangedMilitary(6, 6, Game.getGame().getCivilizations().get(1), UnitName.ARCHER);
        assertTrue(meleeMilitary.ZOCInPath(5,6));
        meleeMilitary.findShortestPathByDijkstra(5,6);
        meleeMilitary.doPlanedMovement();
        assertEquals(5,meleeMilitary.getCoordinatesInMap().get('x')/2);
        assertEquals(6, meleeMilitary.getCoordinatesInMap().get('y')*1);
        assertEquals(-1, meleeMilitary.remainingMovement);

    }
}