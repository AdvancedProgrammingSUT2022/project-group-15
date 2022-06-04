package controller;

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
public class ProfileMenuControllerTest {

    ProfileMenuController controller = new ProfileMenuController();
    String regex = "^(?<nickname>\\S+)$";
    String command = "parsabashari";
    Matcher matcher;
    User user = new User("parsabsh", "oldPassword1234", "parsa", 0);
    static MockedStatic<User> theMock = Mockito.mockStatic(User.class);


    @Test
    public void changeNicknameSuccessfulTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
//        Assert.assertEquals("nickname changed successfully!", controller.changeNickname(matcher));
    }

    @Test
    public void changeNicknameWithCurrentNicknameTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        command = "parsa";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
//        Assert.assertEquals("please enter a new nickname", controller.changeNickname(matcher));
    }

    @Test
    public void changeNicknameWithAnAlreadyTakenNicknameTest(){
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
        theMock.when(() -> User.getUserByNickname("parsabashari")).thenReturn(user);
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
//        Assert.assertEquals("user with nickname parsabashari already exists", controller.changeNickname(matcher));
    }

    @Test
    public void changePasswordSuccessfulTest(){
        regex = "^(?<currentPassword>\\S+) (?<newPassword>\\S+)$";
        command = "oldPassword1234 newPassword1234";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);

//        Assert.assertEquals("password changed successfully!", controller.changePassword(matcher));
    }

    @Test
    public void changePasswordInvalidPasswordTest(){
        regex = "^(?<currentPassword>\\S+) (?<newPassword>\\S+)$";
        command = "oldPassword newPassword1234";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
//        Assert.assertEquals("current password is invalid", controller.changePassword(matcher));
    }

    @Test
    public void changePasswordCurrentPasswordTest(){
        regex = "^(?<currentPassword>\\S+) (?<newPassword>\\S+)$";
        command = "oldPassword1234 oldPassword1234";
        matcher = Pattern.compile(regex).matcher(command);
        System.out.println("matches : " + matcher.matches());
        theMock.when(() -> User.getLoggedInUser()).thenReturn(user);
//        Assert.assertEquals("please enter a new password", controller.changePassword(matcher));
    }
}
