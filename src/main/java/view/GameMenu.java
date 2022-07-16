package view;

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
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.GlobalThings;
import model.Hex;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    @FXML
    public AnchorPane map;

    @FXML
    private ProgressBar currentResearchProgressBar;
    @FXML
    private ImageView currentResearchImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: 7/16/2022 add event handler for map
//        map.requestFocus();
//        map.addEventHandler(KeyEvent.ANY, new EventHandler<Event>() {
//            @Override
//            public void handle(Event event) {
//                System.out.println("sssss");
//                System.exit(0);
//            }
//        });

        fillMap();
        System.out.println("after");
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
            if (Game.getGame().getSelectedCivilization().getVisibilityMap().map.indexOf(hexArrayList) % 2 == 1)
                i = 54;
            for (Hex hex : hexArrayList) {
                ImageView hexView = graphicalHex(hex);
                hexView.setX(i);
                hexView.setY(j);
                map.getChildren().add(hexView);
                i += 108;
            }
            j += 36;
        }
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


}
