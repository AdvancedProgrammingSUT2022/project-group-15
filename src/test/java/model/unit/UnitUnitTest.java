package model.unit;

import enums.UnitName;
import junit.framework.TestCase;
import model.Game;
import model.Hex;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.plaf.multi.MultiTableHeaderUI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UnitUnitTest extends TestCase {

    @Test
    public void testFindShortestPathByDijkstra() {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "", ""));
        Game.startNewGame(users);


        MeleeMilitary meleeMilitary = new MeleeMilitary(0, 0, null, 2, 100, UnitName.ANTITANKGUN);

        Hex hex = mock(Hex.class);
        Game.getGame().map.map.get(1).set(0, hex);
        when(hex.getMovementPrice()).thenReturn(0);
        System.out.println(hex.getMovementPrice());
        int dist = meleeMilitary.findShortestPathByDijkstra(1, 0);
        assertEquals(0, dist);

    }
}