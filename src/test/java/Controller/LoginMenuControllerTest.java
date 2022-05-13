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
    String regex = "";
    String command = "";
    Matcher matcher;
    User user;

    @BeforeEach
    public void setup(){

    }

    @Test
    public void checkPasswordIsWeak(){
        command = "parsabsh mypassword parsa";
        regex = "^(?<username>\\S+) (?<password>\\S+) (?<nickname>\\S+)$";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("password is weak!", controller.createUser(matcher));
    }

    @Test
    public void checkUserWithUsernameExists(){
        user = new User("parsabsh", "parsa1234", "parsa");
        MockedStatic<User> theMock = Mockito.mockStatic(User.class);
        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);

        command = "parsabsh mypassword1234 parsa";
        regex = "^(?<username>\\S+) (?<password>\\S+) (?<nickname>\\S+)$";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user with username parsabsh already exists", controller.createUser(matcher));
    }

    @Test
    public void loginTest(){

    }
}
