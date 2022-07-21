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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import model.*;
import model.unit.SettlerUnit;
import model.unit.Unit;
import model.unit.WorkerUnit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML
    private ImageView applyCheat;
    @FXML
    private TextField cheatTextField;

    private boolean isSelectingTile = false;
    private boolean canChangePopup = true;
    private int codeForFunction = 0;// 1:remove citizen from work    2:lock citizen    3:buy hex

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.setGameMenu(this);
        mapScrollPane.setContent(map);
        mapScrollPane.setPannable(true);
        mapScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        applyCheat.setVisible(false);
        cheatTextField.setVisible(false);
        updateStatusBar();
        updateCurrentResearchStatus();
        fillMap();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setup(map);
        }).start();
        mapScrollPane.setOnKeyReleased(event -> {
            if (event.isControlDown() && event.isAltDown() && event.getCode() == KeyCode.C) {
                applyCheat.setVisible(true);
                cheatTextField.setVisible(true);
            }
        });
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
        } else if (hex.getCity() != null && hex.getCity().getOwner().getVisibilityMap().isCenterOfCity(hex)) {
            hexView = new ImageView(GlobalThings.CITY_IMAGE);
        } else if (hex.getFeature() != Feature.NULL) {
            hexView = new ImageView(hex.getFeature().image);
        } else {
            hexView = new ImageView(hex.getTerrain().image);
        }
        hexView.setFitWidth(144);
        hexView.setFitHeight(144);

        hexView.setOnMouseClicked(event -> {
            if (isSelectingTile) {
                doFunctionForCode(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'));
                return;
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (controller.getSelectedUnit() != null) {
                    createPopupAndGlowForNode(controller.attackTo(hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), null, false, true);
                    fillMap();
                } else if (controller.getSelectedCity() != null) {
                    createPopupAndGlowForNode(controller.cityAttackTo(hex.getCoordinatesInArray().get('x'),
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

    private void doFunctionForCode(int x, int y) {
        isSelectingTile = false;
        switch (codeForFunction) {
            case 1:
                createPopupAndGlowForNode(controller.removeCitizenFromWork(x, y), null, false, false);
                break;
            case 2:
                createPopupAndGlowForNode(controller.lockCitizenToHex(x, y), null, false, false);
                break;
            case 3:
                createPopupAndGlowForNode(controller.buyHex(x, y), null, false, false);
                break;
        }
        fillMap();
        codeForFunction = 0;
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
        popupVBox.getChildren().clear();
        popupLabel.setText(controller.selectCity(city.getName()));
        popupHBox.setVisible(true);
        popup.getContent().add(popupHBox);

        popup.setX(window.getX() + 500);
        popup.setY(window.getY() + 300);
        popup.setAutoHide(true);
        addOptions(false);
        popup.show(window);

        popup.setOnAutoHide(event ->
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        controller.discard(false);
                    }
                }).start());
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

    public void createPopupAndGlowForNode(String message, Node node, boolean OnEndDiscard, boolean isUnit) {
        if (!canChangePopup)
            return;
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
                button = new Button("alert");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.alertSelectedUnit();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("fortify");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.fortifySelectedUnit();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("fortify till healed");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.fortifySelectedUnitTillHeal();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("garrison");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.garrisonSelectedUnit();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("set up");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.setupRangedSelectedUnit();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("pillage");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = controller.pillage();
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
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
            button = new Button("buy building");
            button.setOnAction(event -> {
                String message = controller.getAvailableBuildingsForCity();
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else if (message.length() == 0)
                    createPopupAndGlowForNode("no building is possible now", null, false, false);
                else {
                    String[] buildings = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String building : buildings) {
                        Button unitButton = new Button(building.split(" ")[0]);
                        unitButton.setTooltip(new Tooltip(building));
                        unitButton.setOnAction(event12 -> {
                            String message1 = controller.buyBuilding(building.split(" ")[0]);
                            updateAll();
                            createPopupAndGlowForNode(message1, null, false, false);
                        });
                        popupVBox.getChildren().add(unitButton);
                    }
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("choose building");
            button.setOnAction(event -> {
                String message = controller.getAvailableBuildingsForCity();
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else if (message.length() == 0)
                    createPopupAndGlowForNode("no building is possible now", null, false, false);
                else {
                    String[] buildings = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String building : buildings) {
                        Button unitButton = new Button(building.split(" ")[0]);
                        unitButton.setTooltip(new Tooltip(building));
                        unitButton.setOnAction(event12 -> {
                            String message1 = controller.buildBuilding(building.split(" ")[0]);
                            updateAll();
                            createPopupAndGlowForNode(message1, null, false, false);
                        });
                        popupVBox.getChildren().add(unitButton);
                    }
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("buy hex");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    isSelectingTile = true;
                    codeForFunction = 3;
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("lock citizen");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    isSelectingTile = true;
                    codeForFunction = 2;
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("remove citizen");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    isSelectingTile = true;
                    codeForFunction = 1;
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

    @FXML
    public Popup popup = new Popup();

    public void cityPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        popupLabel.setText(cityNames());
        popup.getContent().add(popupLabel);
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
        popup.getContent().clear();
        popupLabel.setText(unitNames());
        popupHBox.setVisible(true);
        popup.getContent().add(popupLabel);
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
        popup.getContent().clear();
        popupLabel.setText(notif());
        popupHBox.setVisible(true);
        popup.getContent().add(popupLabel);
        popup.setX(window.getX() + 520);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }
    public String notif(){
        String message="<<Notification History>>\n";
        ArrayList<String> allMessages = Controller.getNotificationHistory();
        for (String allMessage : allMessages) {
            message += allMessage + "\n";
        }
        return message;
    }

    public void demographicPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        popupLabel.setText(controller.showDemographicsPanel());
        popupHBox.setVisible(true);
        popup.getContent().add(popupLabel);
        popup.setX(window.getX() + 480);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
    }

    public void openDiplomacyPanel(MouseEvent mouseEvent) {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        popup.getContent().add(vBox);
        popup.setX(window.getX() + 480);
        popup.setY(window.getY() + 105);
        popup.setAutoHide(true);
        popup.show(window);
        Civilization nowCivilization = Game.getGame().getSelectedCivilization();
        for (Civilization civilization : Game.getGame().getCivilizations()) {
            if (civilization == nowCivilization)
                continue;
            HBox hBox = new HBox();
            Label label = new Label();
            if (civilization.getEnemies().contains(nowCivilization))
                label.setText("civilization : " + civilization.getUser().getNickname() + " at war  ");
            else
                label.setText("civilization : " + civilization.getUser().getNickname() + " piece  ");

            label.setStyle("-fx-background-color: white");
            label.setFont(new Font("Arial", 23));
            Button trade = new Button("trade");
            Button warOrPiece = new Button();
            if (civilization.getEnemies().contains(nowCivilization))
                warOrPiece.setText("piece");
            else
                warOrPiece.setText("start war");
            warOrPiece.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (warOrPiece.getText().equals("piece")) {
                        createPopupAndGlowForNode(controller.piece(civilization.getUser().getNickname()), null, false, false);
                    } else {
                        createPopupAndGlowForNode(controller.declareWar(civilization.getUser().getNickname()), null, false, false);
                    }
                    popup.hide();
                }
            });

            trade.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // TODO: 7/20/2022 trade coin and resource
                }
            });
            hBox.getChildren().add(label);
            hBox.getChildren().add(trade);
            hBox.getChildren().add(warOrPiece);


            vBox.getChildren().add(hBox);
        }
    }

    public void applyCheat() {
        String command = cheatTextField.getText();
        String result = "invalid cheat command";
        Matcher matcher;
        if ((matcher = getMatcher(command, "^cheat increase --(?<flag>\\w+) (?<amount>\\d+)$")) != null) {
            int amount = Integer.parseInt(matcher.group("amount"));
            switch (matcher.group("flag")) {
                case "turn":
                    result = controller.cheatIncreaseTurn(amount);
                    break;
                case "gold":
                    result = controller.cheatIncreaseGold(amount);
                    break;
                case "science":
                    result = controller.cheatIncreaseScience(amount);
                    break;
                case "citizens":
                    result = controller.cheatIncreaseCitizens(amount);
                    break;
                case "score":
                    result = controller.cheatIncreaseScore(amount);
                    break;
            }
        } else if (command.equals("cheat open all technologies")) {
            result = controller.cheatOpenAllTechnologies();
        } else if (command.equals("cheat make the whole map visible")) {
            result = controller.cheatMakeMapDetermined();
        } else if (command.equals("cheat win")) {
            result = controller.cheatWin();
        } else if ((matcher = getMatcher(command, "^cheat found city on (?<x>\\d+) (?<y>\\d+)$")) != null) {
            result = controller.cheatFoundCityOn(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        } else if ((command.equals("cheat increase health of selected unit$"))) {
            result = controller.cheatIncreaseHealthOfSelectedUnit();
        } else if ((command.equals("cheat increase power of selected unit"))) {
            result = controller.cheatIncreasePowerOfSelectedUnit();
        }
        createPopupAndGlowForNode(result, null, false, false);
        cheatTextField.clear();
        cheatTextField.setVisible(false);
        applyCheat.setVisible(false);
        updateAll();
    }

    private Matcher getMatcher(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }

    public void pause(MouseEvent mouseEvent) {
        popupVBox.getChildren().clear();
        popupVBox.setSpacing(15);
        popupLabel.setText("Pause");
        Popup popup = new Popup();
        popupVBox.setLayoutX(100);
        popupVBox.setLayoutY(100);
        popupVBox.prefWidth(100);
        popupVBox.prefHeight(100);
        popupVBox.setAlignment(Pos.CENTER);
        popupVBox.setStyle("-fx-border-color: green;");
        popupVBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Button button = new Button("resume");
        button.setStyle("-fx-base: red;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
            }
        });
        Button button1 = new Button("save");
        button1.setStyle("-fx-base: red;");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
                controller.save();
            }
        });
        Button button2 = new Button("menu");
        button2.setStyle("-fx-base: red;");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();
                controller.clean();
                Controller.setGameSettingsMenu(new GameSettingsMenu());
                window.setScene(Controller.getGameSettingsMenu().getScene());
            }
        });
        popupVBox.getChildren().add(button);
        popupVBox.getChildren().add(button1);
        popupVBox.getChildren().add(button2);
        popupVBox.setVisible(true);
        popup.getContent().add(popupVBox);
        popup.setX(window.getX() + 480);
        popup.setY(window.getY() + 200);
        popup.setAutoHide(true);
        popup.show(window);
    }

    public void dontChangePopup() {
        canChangePopup = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                canChangePopup = true;
            }
        }).start();
    }
}
