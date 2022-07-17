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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
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
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    @FXML
    private Label turn;
    @FXML
    private Label year;
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
    @FXML
    private ScrollPane mapScrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapScrollPane.setContent(map);
        mapScrollPane.setPannable(true);
        mapScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        updateStatusBar();
        updateCurrentResearchStatus();
        fillMap();
    }

    private void updateCurrentResearchStatus() {
        currentResearchImageView.setImage(Game.getGame().getSelectedCivilization().getTechnologyInProgress().image);
        currentResearchProgressBar.setProgress((double) Game.getGame().getSelectedCivilization().getScienceStorage() / Game.getGame().getSelectedCivilization().getTechnologyInProgress().cost);
        int remainingTurns = (int) Math.ceil((Game.getGame().getSelectedCivilization().getTechnologyInProgress().cost - Game.getGame().getSelectedCivilization().getScienceStorage())
                / (double) Game.getGame().getSelectedCivilization().getSciencePerTurn());
        currentResearchProgressBar.setTooltip(new Tooltip(remainingTurns + " turns to achieve " + Game.getGame().getSelectedCivilization().getTechnologyInProgress().name));
    }

    public void updateStatusBar() {
        Gold.setText(Integer.toString(Game.getGame().getSelectedCivilization().getGoldStorage()));
        science.setText(Integer.toString(Game.getGame().getSelectedCivilization().getScienceStorage()));
        happiness.setText(Integer.toString(Game.getGame().getSelectedCivilization().getHappiness()));
        turn.setText("Turn : " + Game.getGame().getTurn());
        year.setText("Year : " + Game.getGame().getYear());
    }

    public Group graphicalHex(Hex hex) {
        Group group = new Group();
        ImageView hexView;
        if (hex.getHexVisibility() == HexVisibility.FOG_OF_WAR) {
            hexView = new ImageView(GlobalThings.FOG_OF_WAR_IMAGE);
        } else if (hex.getFeature() != Feature.NULL) {
            hexView = new ImageView(hex.getFeature().image);
        } else {
            hexView = new ImageView(hex.getTerrain().image);
        }
        hexView.setFitWidth(144);
        hexView.setFitHeight(144);
        ImageView resource = new ImageView(hex.getResource().image);
        resource.setFitHeight(40);
        resource.setFitWidth(40);
        resource.setY(5);
        resource.setX(25);


        group.getChildren().add(hexView);
        group.getChildren().add(resource);
        if (hex.getCivilUnit()!=null) {
            ImageView civilUnit = new ImageView(hex.getCivilUnit().getName().getImage());
            civilUnit.setFitHeight(50);
            civilUnit.setFitWidth(50);
            civilUnit.setY(0);
            civilUnit.setX(75);
            group.getChildren().add(civilUnit);
        }
        if (hex.getMilitaryUnit()!=null){
            ImageView militaryUnit = new ImageView(hex.getCivilUnit().getName().getImage());
            militaryUnit.setFitHeight(80);
            militaryUnit.setFitWidth(80);
            militaryUnit.setY(70);
            militaryUnit.setX(40);
            group.getChildren().add(militaryUnit);
        }
        return group;
    }

    public void fillMap() {
        int j = 120;
        Game.getGame().getSelectedCivilization().adjustVisibility();
        for (ArrayList<Hex> hexArrayList : Game.getGame().getSelectedCivilization().getVisibilityMap().map) {
            int i = 100;
            for (Hex hex : hexArrayList) {
                Group hexView = graphicalHex(hex);


                hexView.setLayoutX(i);

                if (hexArrayList.indexOf(hex) % 2 == 1)
                    hexView.setLayoutY(j + 72);
                else
                    hexView.setLayoutY(j);
                map.getChildren().add(hexView);
                i += 108;
            }
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

    public void goToGameMenu(MouseEvent mouseEvent) {
        setup(map);
        window.setScene(Controller.getGameSettingsMenu().getScene());
    }
}
