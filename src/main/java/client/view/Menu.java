package client.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public abstract class Menu {
    protected Stage window;
    protected Scene scene;

    public abstract Scene getScene();

    protected void setup(Node node) {
        window = (Stage) node.getScene().getWindow();
        scene = node.getScene();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
