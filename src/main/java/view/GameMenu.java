package view;

import com.thoughtworks.xstream.io.xml.XomDriver;
import controller.Controller;
import controller.GameMenuController;
import enums.Feature;
import enums.HexVisibility;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import model.Game;
import model.GlobalThings;
import model.Hex;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    public Label popupLabel;
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

        hexView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (window == null) {
                    setup(map);
                }
                if (controller.getSelectedUnit() != null) {
                    createPopupAndGlowForNode(controller.moveSelectedUnitTo(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y')), hexView, false, false);
                    fillMap();
                } else
                    createPopupAndGlowForNode(hexInfo(hex), hexView, false, false);
            }
        });
        ImageView resource = new ImageView(hex.getResource().image);
        resource.setFitHeight(40);
        resource.setFitWidth(40);
        resource.setY(5);
        resource.setX(25);

        group.getChildren().add(hexView);
        group.getChildren().add(resource);
        addUnits(hex, group);
        return group;
    }

    private String hexInfo(Hex hex) {
        String message = "terrain : " + hex.getTerrain().name + "\n"
                + "feature : " + hex.getFeature().name + "\n"
                + "resource : " + hex.getResource().name;
        if (hex.getCivilUnit() != null)
            message += "\n" + "civil unit : " + hex.getCivilUnit().getName().getName();
        if (hex.getMilitaryUnit() != null)
            message += "\n" + "military unit : " + hex.getMilitaryUnit().getName().getName();
        if (hex.getCity() != null)
            message += "\n" + "city : " + hex.getImprovement().name;
        return message;
    }

    private void addUnits(Hex hex, Group group) {
        if (hex.getCivilUnit() != null) {
            ImageView civilUnit = new ImageView(hex.getCivilUnit().getName().getImage());
            civilUnit.setFitHeight(50);
            civilUnit.setFitWidth(50);
            civilUnit.setY(0);
            civilUnit.setX(75);
            group.getChildren().add(civilUnit);
            civilUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (window == null) {
                        setup(map);
                    }
                    createPopupAndGlowForNode(controller.selectCivilUnit(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'))
                            , civilUnit, true, true);
                }
            });
        }
        if (hex.getMilitaryUnit() != null) {
            ImageView militaryUnit = new ImageView(hex.getCivilUnit().getName().getImage());
            militaryUnit.setFitHeight(80);
            militaryUnit.setFitWidth(80);
            militaryUnit.setY(70);
            militaryUnit.setX(40);
            group.getChildren().add(militaryUnit);
            militaryUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (window == null) {
                        setup(map);
                    }
                    createPopupAndGlowForNode(controller.selectMilitaryUnit(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'))
                            , militaryUnit, true, true);
                }
            });
        }
    }

    public void fillMap() {
        map.getChildren().clear();
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

    private void createPopupAndGlowForNode(String message, Node node, boolean OnEndDiscard, boolean isUnit) {
        Popup popup = new Popup();

        popupLabel.setText(message);
        popupLabel.setVisible(true);
        popup.getContent().add(popupLabel);

        popup.setX(window.getX() + 10);
        popup.setY(window.getY() + 700 - popupLabel.getHeight());
        popup.setAutoHide(true);
        System.out.println("pop up started");
        popup.show(window);

        Glow glow = new Glow(0.7);
        node.setEffect(glow);
        popup.setOnAutoHide(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                node.setEffect(null);
                if (OnEndDiscard) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            controller.discard(isUnit);
                        }
                    }).start();
                }
            }
        });
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
