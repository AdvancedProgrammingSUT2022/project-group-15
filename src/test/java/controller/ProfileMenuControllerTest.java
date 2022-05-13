package controller;

import model.Game;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class ProfileMenuControllerTest {

    ProfileMenuController controller = new ProfileMenuController();
    String regex = "^(?<nickname>\\S+)$";
    String command = "parsabashari";
    Matcher matcher;
    User user = new User("parsabsh", "oldPassword1234", "parsa");
    static MockedStatic<User> theMock = Mockito.mockStatic(User.class);


    @Test
    public void changeNicknameSuccessfulTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("nickname changed successfully!", controller.changeNickname(matcher));
    }

    @Test
    public void changeNicknameWithCurrentNicknameTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        command = "parsa";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("please enter a new nickname", controller.changeNickname(matcher));
    }

    @Test
    public void changeNicknameWithAnAlreadyTakenNicknameTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        theMock.when(() -> User.getUserByNickname("parsabashari")).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        Assert.assertEquals("user with nickname parsabashari already exists", controller.changeNickname(matcher));
    }
}
