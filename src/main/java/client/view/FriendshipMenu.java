package client.view;

import client.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FriendshipMenu extends Menu {

    @FXML
    public VBox friendRequestBox;
    public VBox searchNameFounded;
    public Pane friendsNamesBox;
    public javafx.scene.control.TextField text;
    public javafx.scene.control.Label popup;
    public Text names;
    javafx.scene.control.Button button = new javafx.scene.control.Button("accept");
    javafx.scene.control.Button button1 = new javafx.scene.control.Button("reject");
    public ArrayList<String> reqs = new ArrayList<>();
    javafx.scene.control.Label label = new javafx.scene.control.Label();


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
        reqs = (ArrayList<String>) Controller.send("allReqs", Controller.getMyUser().getUsername());
        button.setStyle("-fx-stroke: green;");
        button1.setStyle("-fx-stroke: red;");
        if (reqs.size() > 0) {
            label.setText(reqs.get(0));
            label.setFont(Font.font(15));
            friendRequestBox.setSpacing(10);
            friendRequestBox.getChildren().add(label);
            friendRequestBox.getChildren().add(button);
            friendRequestBox.getChildren().add(button1);
            friendRequestBox.setAlignment(Pos.CENTER);
        }
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controller.send("delete", Controller.getMyUser().getUsername());
                friendRequestBox.getChildren().remove(button);
                friendRequestBox.getChildren().remove(button1);
                friendRequestBox.getChildren().remove(label);
                updateAll();
                friendRequestBox.setVisible(false);
            }
        });
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controller.send("deleteAgain", Controller.getMyUser().getUsername());
                friendRequestBox.getChildren().remove(button);
                friendRequestBox.getChildren().remove(button1);
                friendRequestBox.getChildren().remove(label);
                friendRequestBox.setVisible(false);
                updateAll();
            }
        });

    }

    public void updateAll() {
        if (reqs.size() > 0) {
            Text text = new Text();
            javafx.scene.control.Button button = new javafx.scene.control.Button("accept");
            javafx.scene.control.Button button1 = new javafx.scene.control.Button("reject");
            javafx.scene.control.Label label = new javafx.scene.control.Label();
            label.setText(reqs.get(0));
            label.setFont(Font.font(15));
            friendRequestBox.setSpacing(10);
            friendRequestBox.getChildren().add(label);
            friendRequestBox.getChildren().add(button);
            friendRequestBox.getChildren().add(button1);
            friendRequestBox.setVisible(true);
        }
        ArrayList<String> allFriends = (ArrayList<String>) Controller.send("friend", Controller.getMyUser().getUsername());
        String name = "";
        for (int i = 0; i < allFriends.size(); i++) {
            name += allFriends.get(i) + "\t";
            if (i % 4 == 0 && i > 0) name += "\n";
        }
        names.setText(name);
        names.setVisible(true);
    }


    public String send(MouseEvent mouseEvent) {
        String name = text.getText();
        String out = (String) Controller.send("sendReq", name, Controller.getMyUser().getUsername());
        popup.setText(out);
        popup.setVisible(true);
        System.out.println(out);
        return out;
    }


    public void openMainMenu(MouseEvent mouseEvent) {
        setup(friendRequestBox);
        Controller.send("change menu Main");
        window.setScene(Controller.getMainMenu().getScene());
    }
}
