package controller;
import org.junit.Assert;
import org.junit.Test;
public class ControllerTest {

    @Test
    public void addNotificationTest(){
        Assert.assertEquals("test message", Controller.addNotification(0, "test message"));
    }

}
