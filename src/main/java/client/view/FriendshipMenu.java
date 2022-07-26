package client.view;

import client.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class FriendshipMenu extends Menu {
    @FXML
    public AnchorPane pane;
    public Group friendRequests;
    public Group searchPlayer;
    public VBox friendRequestBox;
    public VBox searchNameFounded;
    public Pane friendsNamesBox;
    public Group friendsName;
    public javafx.scene.control.TextField text;


    public Scene getScene() {
        try {
            AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/friendShipMenu.fxml").toExternalForm()));
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public void initialize() throws IOException {

    }


    public String send(MouseEvent mouseEvent) {
        String name = text.getText();
        String out = (String) Controller.send("sendReq" , name , Controller.getMyUser().getUsername());
        System.out.println(out);
        return out;
    }


}
