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

    @Mock
    User user;

    @BeforeEach
    public void setup(){
//        MockedStatic<User> theMock = Mockito.mockStatic(User.class);
//        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);
//        theMock.when(() -> User.getUserByNickname("parsa")).thenReturn(user);
    }

    @Test
    public void checkPasswordIsWeak(){
        LoginMenuController controller = new LoginMenuController();
        String command = "user create --username parsabsh --password mypassword --nickname parsa";
        String regex = "^user create (-u|--username) (?<username>\\S+) (-p|--password) (?<password>\\S+) (-n|--nickname) (?<nickname>\\S+)$";
        Matcher matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("password is weak!", controller.createUser(matcher));
    }

    @Test
    public void checkUserWithUsernameExists(){

    }

    @Test
    public void loginTest(){

    }
}
