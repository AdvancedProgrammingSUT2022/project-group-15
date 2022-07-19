package view;

import controller.Controller;
import enums.Technology;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnologyTree extends Menu implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    private final ArrayList<TechnologyNode> technologyNodes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for (Technology technology : Technology.values()) {
            technologyNodes.add(new TechnologyNode(technology));
        }
        ((AnchorPane) scrollPane.getContent()).getChildren().addAll(technologyNodes);
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

    public void back() {
        setup(scrollPane);
        window.setScene(Controller.getGameMenu().getScene());
    }
}
