package view;

import com.sun.org.apache.bcel.internal.generic.DCONST;
import controller.Controller;
import controller.GameMenuController;
import enums.Feature;
import enums.HexVisibility;
import enums.Improvement;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import model.City;
import model.Game;
import model.GlobalThings;
import model.Hex;
import model.unit.SettlerUnit;
import model.unit.Unit;
import model.unit.WorkerUnit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMenu extends Menu implements Initializable {
    private final GameMenuController controller = new GameMenuController();
    @FXML
    public VBox cityInfo;
    @FXML
    public Label popupLabel;
    @FXML
    public HBox popupHBox;
    @FXML
    public VBox popupVBox;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setup(map);
            }
        }).start();
    }

    private void updateAll() {
        updateStatusBar();
        updateCurrentResearchStatus();
        fillMap();
    }

    private void updateCurrentResearchStatus() {
        if (Game.getGame().getSelectedCivilization().getCities().isEmpty()) {
            currentResearchImageView.setVisible(false);
            currentResearchProgressBar.setVisible(false);
            return;
        } else {
            currentResearchImageView.setVisible(true);
            currentResearchProgressBar.setVisible(true);
        }

        if (Game.getGame().getSelectedCivilization().getTechnologyInProgress() == null) {
            currentResearchProgressBar.setVisible(false);
            currentResearchImageView.setImage(new Image(getClass().getResource("/icons/gear.png").toExternalForm()));
            return;
        } else {
            currentResearchProgressBar.setVisible(true);
        }

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

        hexView.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.SECONDARY) {
                if (controller.getSelectedUnit() != null) {
                    createPopupAndGlowForNode(controller.attackTo(hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), null, false, true);
                    fillMap();
                }
            } else {
                if (controller.getSelectedUnit() != null) {
                    createPopupAndGlowForNode(controller.moveSelectedUnitTo(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y')), null, false, false);
                    updateAll();
                } else
                    createPopupAndGlowForNode(hexInfo(hex), hexView, false, false);
            }
        });
        group.getChildren().add(hexView);
        if (hex.getHexVisibility() == HexVisibility.FOG_OF_WAR)
            return group;

        addResourceAndRuin(hex, group);
        addCity(hex, group);

        if (hex.getHexVisibility() == HexVisibility.DETERMINED) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.4);
            colorAdjust.setContrast(-0.4);
            hexView.setEffect(colorAdjust);
            return group;
        }

        addUnits(hex, group);
        return group;
    }

    private void addCity(Hex hex, Group group) {
        if (hex.getCity() != null) {
            if (hex.getCity().getOwner().getVisibilityMap().isCenterOfCity(hex)) {
                Label label = new Label(hex.getCity().getName());
                label.setStyle("-fx-background-color: purple;-fx-text-fill: #04e2ff");
                label.setLayoutX(60);
                label.setLayoutY(5);
                label.setOnMouseClicked(event -> createCityPage(hex.getCity()));
                group.getChildren().add(label);
            }
        }
    }

    private void addResourceAndRuin(Hex hex, Group group) {
        ImageView resource = new ImageView(hex.getResource().image);
        resource.setFitHeight(40);
        resource.setFitWidth(40);
        resource.setY(5);
        resource.setX(25);
        group.getChildren().add(resource);

        if (hex.hasRuins()) {
            ImageView ruins = new ImageView(GlobalThings.RUINS_IMAGE);
            ruins.setFitWidth(40);
            ruins.setFitHeight(40);
            ruins.setX(25);
            ruins.setY(95);
            group.getChildren().add(ruins);
        }
    }

    private void createCityPage(City city) {
        Popup popup = new Popup();

        popupLabel.setText(controller.selectCity(city.getName()));
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);

        popup.setX(window.getX() + 500);
        popup.setY(window.getY() + 300);
        popup.setAutoHide(true);
        addOptions(false);
        popup.show(window);

        popup.setOnAutoHide(event -> controller.discard(false));
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
            message += "\n" + "city : " + hex.getCity().getName();
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
            civilUnit.setOnMouseClicked(event -> {
                createPopupAndGlowForNode(controller.selectCivilUnit(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'))
                        , civilUnit, true, true);
            });
        }
        if (hex.getMilitaryUnit() != null) {
            ImageView militaryUnit = new ImageView(hex.getMilitaryUnit().getName().getImage());
            militaryUnit.setFitHeight(60);
            militaryUnit.setFitWidth(60);
            militaryUnit.setY(75);
            militaryUnit.setX(45);
            group.getChildren().add(militaryUnit);
            militaryUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    createPopupAndGlowForNode(controller.selectMilitaryUnit(hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), militaryUnit, true, true);
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
        popupVBox.getChildren().clear();
        Popup popup = new Popup();

        popupLabel.setText(message);
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);

        popup.setX(window.getX() + 10);
        popup.setY(window.getY() + 730 - popupHBox.getHeight());
        popup.setAutoHide(true);
        if (OnEndDiscard) {
            addOptions(isUnit);
        }
        popup.show(window);
        Glow glow = new Glow(0.7);
        if (node != null)
            node.setEffect(glow);
        popup.setOnAutoHide(event -> {
            if (node != null)
                node.setEffect(null);
            popupVBox.getChildren().clear();
            if (OnEndDiscard) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        controller.discard(isUnit);
                    }
                }).start();
            }
        });
    }

    private void addOptions(boolean isUnit) {
        if (isUnit) {
            Button button = new Button("delete unit");
            button.setOnAction(event -> {
                String message = controller.deleteSelectedUnit();
                updateAll();
                createPopupAndGlowForNode(message, null, false, false);
            });
            popupVBox.getChildren().add(button);
            button = new Button("sleep unit");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = controller.sleepSelectedUnit();
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("wake unit");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = controller.wakeUpSelectedUnit();
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("cancel mission");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = controller.cancelSelectedUnitMission();
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            Unit unit = controller.getSelectedUnit();
            if (unit instanceof SettlerUnit) {

                button = new Button("found city");
                button.setOnAction(event -> {
                    String message = controller.foundCity();
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                });
                popupVBox.getChildren().add(button);

            } else if (unit instanceof WorkerUnit) {
                button = new Button("remove jungle or swamp");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.removeJungleOrSwamp();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("remove route");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.removeRoute();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("repair");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.repair();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("build improvement");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Improvement> improvements = Game.getGame().getSelectedCivilization().getOpenedImprovements();
                        popupVBox.getChildren().clear();
                        for (Improvement improvement : improvements) {
                            Button improvementButton = new Button(improvement.name);
                            improvementButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    createPopupAndGlowForNode(controller.buildImprovement(improvement.name), null, false, false);
                                }
                            });
                            popupVBox.getChildren().add(improvementButton);
                        }
                    }
                });
                popupVBox.getChildren().add(button);
            } else {

            }
        } else {
            Button button = new Button("choose unit");
            button.setOnAction(event -> {
                String message = controller.getAvailableUnitsInCity();
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else {
                    String[] units = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String unit : units) {
                        Button unitButton = new Button(unit);
                        unitButton.setOnAction(event1 -> createPopupAndGlowForNode(controller.chooseProductionForUnit(unit), null, false, false));
                        popupVBox.getChildren().add(unitButton);
                    }
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("buy unit");
            button.setOnAction(event -> {
                String message = controller.getAvailableUnitsInCity();
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else {
                    String[] units = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String unit : units) {
                        Button unitButton = new Button(unit);
                        unitButton.setOnAction(event12 -> {
                            String message1 = controller.buyUnit(unit);
                            updateAll();
                            createPopupAndGlowForNode(message1, null, false, false);
                        });
                        popupVBox.getChildren().add(unitButton);
                    }
                }
            });
            popupVBox.getChildren().add(button);

        }
    }

    private void addButton(String message) {
        popupVBox.getChildren().add(new Button(message));
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

        window.setScene(Controller.getGameSettingsMenu().getScene());
    }

    public void openTechnologyTree(MouseEvent mouseEvent) {

        window.setScene(Controller.getTechnologyTree().getScene());
    }

    public void cityPanel(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        popupLabel.setText(cityNames());
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);
        popup.setX(window.getX() + 387);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }

    private String cityNames() {
        String message = "";
        if (Game.getGame().getSelectedCivilization().getCities().size() == 0)
            return "no city";
        else {
            for (City city : Game.getGame().getSelectedCivilization().getCities()) {
                message += city.getName() + "\n";
            }
            return message;
        }
    }

    public void unitPanel(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        popupLabel.setText(unitNames());
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);
        popup.setX(window.getX() + 410);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }

    private String unitNames() {
        String message = "";
        if (Game.getGame().getSelectedCivilization().getUnits().size() == 0)
            return "no unit";
        for (Unit unit : Game.getGame().getSelectedCivilization().getUnits()) {
            message += unit.getName() + "\n";
        }
        return message;
    }

    public void nextTurn() {
        createPopupAndGlowForNode(controller.changeTurn(false), null, false, false);
        updateAll();
    }

    public void notificationHistory(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        popupLabel.setText(controller.showNotificationHistory());
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);
        popup.setX(window.getX() + 520);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }

    public void demographicPanel(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        popupLabel.setText(controller.showDemographicsPanel());
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);
        popup.setX(window.getX() + 480);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }
}
