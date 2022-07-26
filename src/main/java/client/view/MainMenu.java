package client.view;

import client.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import client.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu extends Menu implements Initializable {

    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView avatarImage;
    @FXML
    private Label username;
    @FXML
    private Label score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  Controller.updateUser();
        User user = Controller.getMyUser();
        avatarImage.setImage(user.getAvatar().image);
        username.setText(user.getUsername());
        score.setText(String.valueOf(user.getScore()));
        Tooltip.install(exitButton, new Tooltip("Exit"));
    }

    @Override
    public Scene getScene() {
        try {
            AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/mainMenu.fxml").toExternalForm()));
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public void exitGame() {
        setup(avatarImage);
        window.close();
    }

    public void openProfileMenu() {
        setup(avatarImage);
        Controller.send("change menu Profile");
        window.setScene(Controller.getProfileMenu().getScene());
    }

    public void showScoreBoard(MouseEvent e) {
        setup(avatarImage);
        Controller.send("change menu Score");
        window.setScene(Controller.getScoreBoard().getScene());
    }

    public void startNewGame() {
        setup(avatarImage);
        Controller.send("change menu GameSetting");
        window.setScene(Controller.getGameSettingsMenu().getScene());
    }
}


