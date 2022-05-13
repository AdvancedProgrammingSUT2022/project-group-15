package Controller;

import controller.LoginMenuController;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class LoginMenuControllerTest {
    LoginMenuController controller = new LoginMenuController();
    String regex = "^(?<username>\\S+) (?<password>\\S+) (?<nickname>\\S+)$";
    String command = "parsabsh mypassword parsa";
    Matcher matcher;
    User user = new User("", "", "");
    static MockedStatic<User> theMock = Mockito.mockStatic(User.class);


    /* user create tests */
    @Test
    public void checkPasswordIsWeak(){
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("password is weak!", controller.createUser(matcher));
    }

    @Test
    public void checkUserWithUsernameExists(){
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);

        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user with username parsabsh already exists", controller.createUser(matcher));
    }

    @Test
    public void checkUserWithNicknameExists(){
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(null);
        theMock.when(() -> User.getUserByNickname("parsa")).thenReturn(user);

        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user with nickname parsa already exists", controller.createUser(matcher));
    }

    @Test
    public void checkCreateUserSuccessful(){
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(null);
        theMock.when(() -> User.getUserByNickname("parsa")).thenReturn(null);

        command = "parsabsh password1234 parsa";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user created successfully!", controller.createUser(matcher));
    }

    /* user login tests */
    @Test
    public void checkIncorrectPassword(){
        regex = "^(?<username>\\S+) (?<password>\\S+)$";
        command = "parsabsh password1234";
        user = new User("parsabsh", "passwordNotMatch", "");
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("Username and password didn't match!", controller.login(matcher));
    }

    @Test
    public void checkLoginSuccessful(){
        regex = "^(?<username>\\S+) (?<password>\\S+)$";
        command = "parsabsh password1234";
        user = new User("parsabsh", "password1234", "");
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user logged in successfully!", controller.login(matcher));
    }

    @Test
    public void checkIsUserLoggedInFalse(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(null);
        Assert.assertFalse(controller.isUserLoggedIn());
    }

    @Test
    public void checkIsUserLoggedInTrue(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        Assert.assertTrue(controller.isUserLoggedIn());
    }
}
