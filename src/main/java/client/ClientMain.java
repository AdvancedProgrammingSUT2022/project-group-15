package client;

import client.controller.Controller;
import client.enums.Technology;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.stage.Stage;
import server.model.Hex;


public class ClientMain extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Gson gson = new Gson();
        //System.out.println(gson.toJson(new Hex()));
        Controller controller = new Controller();
        controller.run(primaryStage);
    }
}
