package Controller;

import controller.Controller;
import controller.LoginMenuController;
import junit.framework.TestCase;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    @Test
    public void loginTest(){

    }
}
