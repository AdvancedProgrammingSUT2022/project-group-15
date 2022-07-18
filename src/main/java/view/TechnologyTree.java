package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TechnologyTree extends Menu implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/TechnologyTree.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }
}
