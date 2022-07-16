package view;

import controller.Controller;
import controller.GameMenuController;
import enums.Feature;
import enums.HexVisibility;
import enums.Resource;
import enums.Terrain;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Civilization;
import model.Game;
import model.GlobalThings;
import model.Hex;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    public Label turn;
    public Label year;
    @FXML
    private AnchorPane map;
    @FXML
    private Label happiness;
    @FXML
    private Label science;
    @FXML
    private Label Gold;

    @FXML
    private ProgressBar currentResearchProgressBar;
    @FXML
    private ImageView currentResearchImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gold.setText(Integer.toString(getGold()));
        science.setText(Integer.toString(getScience()));
        happiness.setText(Integer.toString(getHappiness()));
        turn.setText("Turn : " + Game.getGame().getTurn());
        year.setText("Year : " + Game.getGame().getYear());
        fillMap();
        System.out.println("after");
    }

    private int getHappiness() {
        int result = 0;
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            result += civilization.getHappiness();
        }
        return result;
    }

    private int getScience() {
        int result = 0;
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            result += civilization.getScienceStorage();
        }
        return result;
    }

    public ImageView graphicalHex(Hex hex) {
        if (hex.getHexVisibility() == HexVisibility.FOG_OF_WAR) {
            return new ImageView(GlobalThings.FOG_OF_WAR_IMAGE);
        }
        if (hex.getFeature() != Feature.NULL) {
            return new ImageView(hex.getFeature().image);
        }
        return new ImageView(hex.getTerrain().image);
    }

    public void fillMap() {
        int i = 0, j = 0;
        Game.getGame().getSelectedCivilization().adjustVisibility();
        for (ArrayList<Hex> hexArrayList : Game.getGame().getSelectedCivilization().getVisibilityMap().map) {
            i = 0;
            //if (Game.getGame().getSelectedCivilization().getVisibilityMap().map.indexOf(hexArrayList) % 2 == 1)
            //    i = 54;
            for (Hex hex : hexArrayList) {
                ImageView hexView = graphicalHex(hex);
                hexView.setFitHeight(144);
                hexView.setFitWidth(144);
                hexView.setX(i);
                if (hexArrayList.indexOf(hex) % 2 == 1)
                    hexView.setY(j + 72);
                else
                    hexView.setY(j);
                map.getChildren().add(hexView);
                //i += 108;
                i += 108;
            }
            //j += 36;
            j += 144;
        }
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameMenu.fxml").toExternalForm()));
                scene = new Scene(root);
                root.getChildren().get(0).requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    private int getGold() {
        int result = 0;
        result = Game.getGame().getAverageGold() * Game.getGame().getCivilizations().size();
        return result;
    }

    public void dragMap(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.setLayoutY(map.getLayoutY() - 10);
                break;
            case DOWN:
                map.setLayoutY(map.getLayoutY() + 10);
                break;
            case LEFT:
                map.setLayoutX(map.getLayoutX() - 10);
                break;
            case RIGHT:
                map.setLayoutX(map.getLayoutX() + 10);
                break;
        }

    }

//    public void dragMap() {
//        //TODO : 1) change from onKeyReleased to OnDragDetected    2) implement dragging the map
//        System.out.println("hello");
//    }
public void goToGameMenu(MouseEvent mouseEvent) {
    setup(map);
    window.setScene(Controller.getGameSettingsMenu().getScene());
}

}
