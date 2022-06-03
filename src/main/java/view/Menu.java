package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
