package model.unit;

import junit.framework.TestCase;
import model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest extends TestCase {
    @Mock
    MilitaryUnit militaryUnit;

    @Test
    public void testDoPlanedMovement() {
    }

    @Test
    public void testFindShortestPathByDijkstra() {

        Game.startNewGame(new ArrayList<>());
//        when(militaryUnit.getOwner()).then(null);
//        System.out.println(militaryUnit.getOwner());
//
//        MeleeMilitary meleeMilitary = new MeleeMilitary(0, 0, null, 2, 100, UnitName.ANTITANK_GUN);
//        System.out.println(23123);
//
//
//        hex = Mockito.mock(Hex.class);
//        Game.getGame().map.map.get(2).set(0, hex);
//        when(hex.getMovementPrice()).thenReturn(5);
//        int dist = meleeMilitary.findShortestPathByDijkstra(2, 0);
//        assertEquals(dist, 5);

    }
}