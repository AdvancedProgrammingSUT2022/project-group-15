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
    }

    @Test
    public void checkPasswordIsWeak(){
//        MockedStatic<User> theMock = Mockito.mockStatic(User.class);
//        theMock.when(() -> User.getUserByUsername("parsabsh")).thenReturn(user);
//        theMock.when(() -> User.getUserByNickname("parsa")).thenReturn(user);
        LoginMenuController controller = new LoginMenuController();
        Matcher matcher = Pattern.compile("^(?<username>\\S+) (?<password>\\S+) (?<nickname>\\S+)$").matcher("parsabsh password parsa");
        Assert.assertEquals("password is weak!", controller.createUser(matcher));
    }

    @Test
    public void loginTest(){

    }
}
