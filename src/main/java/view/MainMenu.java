package view;

import controller.Controller;
import controller.MainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu extends Menu implements Initializable {
    private final MainMenuController controller = new MainMenuController();

    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView avatarImage;
    @FXML
    private Label username;
    @FXML
    private Label score;
    @FXML
    private Button profileButton;
    @FXML
    private Button scoreBoardButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (controller.getLoggedInPlayer() == null) {
            profileButton.setDisable(true);
            scoreBoardButton.setDisable(true);
            avatarImage.setCursor(Cursor.DEFAULT);
            avatarImage.setOnMouseClicked(null);
        } else {
            avatarImage.imageProperty().bind(controller.getLoggedInPlayer().avatarProperty());
            username.textProperty().bind(controller.getLoggedInPlayer().usernameProperty());
            score.textProperty().bind(controller.getLoggedInPlayer().scoreProperty().asString());
        }
        Tooltip.install(exitButton, new Tooltip("Exit"));
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/mainMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void exitGame() {
        setup(avatarImage);
        window.close();
    }

    public void openProfileMenu() {
        setup(avatarImage);
        window.setScene(Controller.getProfileMenu().getScene());
    }

    public void showScoreBoard(MouseEvent e) {
        setup(avatarImage);
        window.setScene(Controller.getScoreBoard().getScene());
    }

    public void startNewGame() {
        setup(avatarImage);
//        window.setScene(Controller.getGameSettings().getScene());
    }
}