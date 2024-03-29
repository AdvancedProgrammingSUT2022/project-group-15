package client;

import client.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientMain extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.run(primaryStage);
    }
}
