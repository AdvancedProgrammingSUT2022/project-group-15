package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSettingsMenu extends Menu implements Initializable {
    @FXML
    public Button cancelButton;
    @FXML
    public VBox friendsInGame;
    @FXML
    public Label information;
    @FXML
    public TextField usernameTextField;
    @FXML
    public ChoiceBox<Integer> numberOfPlayersBox;

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameSettingsMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfPlayersBox.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
            setup(cancelButton);
            window.setScene(Controller.getMainMenu().getScene());
    }

    public void clicked(MouseEvent mouseEvent) {
        System.out.println("sdfasvadfvadf");
        System.out.println("clicked");
    }

    public void differ(MouseEvent mouseEvent) {
        cancelButton.setVisible(!cancelButton.isVisible());
    }
}
//else if (command.startsWith("play game ")) {
//        HashMap<Integer, String> usernames = new HashMap<>();
//        matcher = Pattern.compile("(?:-p|--player)(?<number>\\d) (?<username>\\S+)").matcher(command);
//        while (matcher.find()) {
//        usernames.put(Integer.parseInt(matcher.group("number")), matcher.group("username"));
//        }
//        if (usernames.size() <= 1) {
//        System.out.println("not enough players");
//        } else {
//        System.out.println(controller.startGame(usernames));
//        System.out.println("you are in the game menu");
//        return "game menu";
//        }
//}