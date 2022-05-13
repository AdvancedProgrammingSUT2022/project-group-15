package model.unit;

import enums.Feature;
import enums.Resource;
import enums.Terrain;
import enums.UnitName;
import model.Civilization;
import model.Game;
import model.Hex;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UnitUnitTest {

    @Test
    public void testFindShortestPathByDijkstra() {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", ""));
        Game.startNewGame(users);


        Civilization mockedCivilization = mock(Civilization.class);
        MeleeMilitary meleeMilitary = new MeleeMilitary(0, 0, mockedCivilization, UnitName.ANTITANKGUN);

        Hex hex = mock(Hex.class);
        Game.getGame().map.map.get(1).set(0, hex);
        when(hex.getMovementPrice()).thenReturn(0);
        Assert.assertEquals( 0, hex.getMovementPrice(),0.1);
        double dist = meleeMilitary.findShortestPathByDijkstra(1, 0);
        Assert.assertEquals(0, dist,0.1);
    }

    @Test
    public void testMoveToMountain() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", ""));
        Game.startNewGame(users);

        Civilization mockedCivilization = mock(Civilization.class);
        MeleeMilitary meleeMilitary = new MeleeMilitary(5, 5, mockedCivilization, UnitName.ANTITANKGUN);
        Game.getGame().map.map.get(5).set(6, new Hex(Terrain.MOUNTAIN, Feature.NULL, Resource.NULL, false, 5, 6));
        double dist = meleeMilitary.findShortestPathByDijkstra(5, 6);

        System.out.println(dist);
    }
}