package controller;

import model.Game;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {
    MainMenuController controller = new MainMenuController();
    static MockedStatic<Game> gameMockedStatic = Mockito.mockStatic(Game.class);
    static MockedStatic<User> userMockedStatic = Mockito.mockStatic(User.class);

    @Mock
    User user;

    @Test
    public void logoutTest() {
        User.setLoggedInUser(user);
        controller.logout();
        Assert.assertNull(User.getLoggedInUser());
    }

    @Test
    public void isGameStartedTest() {
        gameMockedStatic.when(() -> Game.getGame()).thenReturn(null);
        Assert.assertFalse(controller.isGameStarted());
    }

    @Test
    public void startGameSuccessfulTest() {
        userMockedStatic.when(() -> User.getUserByUsername("")).thenReturn(user);
        HashMap<Integer, String> usernames = new HashMap<>();
        usernames.put(1, "");
        usernames.put(2, "");
        usernames.put(3, "");
        usernames.put(4, "");
        Assert.assertEquals("a new game started with 4 players", controller.startGame(usernames));
    }

    @Test
    public void startGameWithInvalidNumbersTest() {
        userMockedStatic.when(() -> User.getUserByUsername("")).thenReturn(user);
        HashMap<Integer, String> usernames = new HashMap<>();
        usernames.put(1, "");
        usernames.put(2, "");
        usernames.put(3, "");
        usernames.put(5, "");
        Assert.assertEquals("invalid player numbers", controller.startGame(usernames));
    }
}
