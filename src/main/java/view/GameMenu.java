package view;

import controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Hex;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();

    @FXML
    private ProgressBar currentResearchProgressBar;
    @FXML
    private ImageView currentResearchImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameMenu.fxml").toExternalForm()));
                scene = new Scene(root);
//                root.getChildren().get(0).requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public Image[] graphicalHex(Hex hex) {
        // TODO : Parsa
        return null;
    }

}
