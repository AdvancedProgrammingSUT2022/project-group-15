package controller;

import org.junit.Assert;
import org.junit.Test;
import server.controller.Controller;

public class ControllerTest {

    @Test
    public void addNotificationTest() {
        Assert.assertEquals("test message", Controller.addNotification(0, "test message"));
    }

}
