package view;

import controller.GameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Civilization;
import model.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    @FXML
    public Label goldAmount;
    public Label happiness;
    public Label Science;
    @FXML
    private ProgressBar currentResearchProgressBar;
    @FXML
    private ImageView currentResearchImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goldAmount.setText(Integer.toString(getMoney()));
        happiness.setText(Integer.toString(getHappiness()));
        Science.setText(Integer.toString(getScience()));
    }

    private int getScience() {
        int result = 0;
      //  for (Civilization civilization : Game.getGame().getCivilizations()) {
      //      result+=civilization.getScienceStorage();
      //  }
        return result;
    }

    private int getHappiness() {
        int result = 0;
        // for (Civilization civilization : Game.getGame().getCivilizations()) {
        //   result += civilization.getHappiness();
        //  }
        return result;
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

    private int getMoney() {
        //  int result = Game.getGame().getAverageGold() * Game.getGame().getCivilizations().size();
        int result = 10;
        return result;
    }

    private String a() {
        return "sdasa";
    }

}
