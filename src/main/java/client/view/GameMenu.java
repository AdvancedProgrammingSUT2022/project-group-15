package client.view;


import client.controller.Controller;
import client.enums.Terrain;
import client.enums.UnitName;
import client.enums.Technology;
import client.model.GlobalThings;

import server.enums.Feature;
import server.enums.HexVisibility;
import server.enums.Improvement;
import server.enums.Resource;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;

import server.model.City;
import server.model.Civilization;
import server.model.Game;
import server.model.Hex;
import server.model.unit.SettlerUnit;
import server.model.unit.Unit;
import server.model.unit.WorkerUnit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu extends Menu implements Initializable {
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

    public void updateAll() {
        updateStatusBar();
        updateCurrentResearchStatus();
        while (true) {
            try {
                fillMap();
                break;
            } catch (Exception e) {
                System.out.println("a problem please wait");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void updateCurrentResearchStatus() {
        if (! (boolean) Controller.send("hasCity")) {
            currentResearchImageView.setVisible(false);
            currentResearchProgressBar.setVisible(false);
            return;
        } else {
            currentResearchImageView.setVisible(true);
            currentResearchProgressBar.setVisible(true);
        }

        if (! (boolean) Controller.send("hasTechnologyInProgress")) {
            currentResearchProgressBar.setVisible(false);
            currentResearchImageView.setImage(new Image(getClass().getResource("/icons/gear.png").toExternalForm()));
            return;
        } else {
            currentResearchProgressBar.setVisible(true);
        }

        currentResearchImageView.setImage((Technology.valueOf((String) Controller.send("getTechnologyInProgress"))).image);
        currentResearchProgressBar.setProgress((double) Controller.send("getScience") / ((Technology.valueOf((String) Controller.send("getTechnologyInProgress"))).cost));
        int remainingTurns = (int) Math.ceil((((Technology.valueOf((String) Controller.send("getTechnologyInProgress"))).cost - (double) Controller.send("getScience"))
                / (double) Controller.send("getSciencePerTurn")));
        currentResearchProgressBar.setTooltip(new Tooltip(remainingTurns + " turns to achieve " + ((Technology.valueOf((String) Controller.send("getTechnologyInProgress"))).name)));
    }

    public void updateStatusBar() {
        Gold.setText(Double.toString((Double) Controller.send("getGoldStorage")));
        science.setText(Double.toString((Double) Controller.send("getScience")));
        happiness.setText(Double.toString((Double) Controller.send("getHappiness")));
        turn.setText("Turn : " + ((Double) Controller.send("getTurn")).intValue());
        year.setText("Year : " + ((Double) Controller.send("getYear")).intValue());
    }

    public Group graphicalHex(Hex hex) {

        Group group = new Group();
        ImageView hexView;
        if (hex.getHexVisibility() == HexVisibility.FOG_OF_WAR) {
            hexView = new ImageView(GlobalThings.FOG_OF_WAR_IMAGE);
        } else if (hex.getCity() != null && hex.isCenterOfCity()) {
            hexView = new ImageView(GlobalThings.CITY_IMAGE);
        } else if (hex.getFeature() != Feature.NULL) {
            hexView = new ImageView(client.enums.Feature.valueOf(hex.getFeature().name()).image);
        } else {
            hexView = new ImageView(Terrain.valueOf(hex.getTerrain().name()).image);
        }
        hexView.setFitWidth(144);
        hexView.setFitHeight(144);

        hexView.setOnMouseClicked(event -> {
            if (isSelectingTile) {
                doFunctionForCode(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'));
                return;
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (Controller.send("getSelectedUnit") != null) {
                    String message = (String) Controller.send("attackTo", hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'));
                    if (message.equals("city has fallen")) {
                        message = message + ". what do you do with the captured city?";
                    }
                    createPopupAndGlowForNode(message, null, false, true);
                    if (message.startsWith("city has fallen")) {
                        Button button = new Button("capture city");
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String message = (String) Controller.send("captureCity");
                                updateAll();
                                createPopupAndGlowForNode(message, null, false, false);
                            }
                        });
                        popupVBox.getChildren().add(button);
                        button = new Button("destroy city");
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String message = (String) Controller.send("destroyCity");
                                updateAll();
                                createPopupAndGlowForNode(message, null, false, false);
                            }
                        });
                        popupVBox.getChildren().add(button);

                    }

                    fillMap();
                } else if ((Boolean) Controller.send("hasSelectedCity")) {
                    createPopupAndGlowForNode((String) Controller.send("cityAttackTo", hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), null, false, true);
                    fillMap();
                }

            } else {
                if (Controller.getSelectedUnit() != null) {
                    createPopupAndGlowForNode((String) Controller.send("moveSelectedUnitTo", hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), null, false, false);
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
                createPopupAndGlowForNode((String) Controller.send("removeCitizenFromWork", x, y), null, false, false);
                break;
            case 2:
                createPopupAndGlowForNode((String) Controller.send("lockCitizenToHex", x, y), null, false, false);
                break;
            case 3:
                createPopupAndGlowForNode((String) Controller.send("buyHex", x, y), null, false, false);
                break;
        }
        fillMap();
        codeForFunction = 0;
    }

    private void addCity(Hex hex, Group group) {
        if (hex.getCity() != null) {

            if (hex.isCenterOfCity()) {
                Label label = new Label(hex.getCity().getName());
                label.setStyle("-fx-background-color: purple;-fx-text-fill: #04e2ff");
                label.setLayoutX(60);
                label.setLayoutY(5);
                label.setOnMouseClicked(event -> createCityPage(hex.getCity().getName()));
                group.getChildren().add(label);
            }
        }
    }

    private void addResourceAndRuin(Hex hex, Group group) {
        ImageView resource = new ImageView(client.enums.Resource.valueOf(hex.getResource().name()).image);
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

    private void createCityPage(String cityName) {
        Popup popup = new Popup();
        popupVBox.getChildren().clear();
        popupLabel.setText((String) Controller.send("selectCity", cityName));
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
                        Controller.send("discard", false);
                    }
                }).start());
    }

    private String hexInfo(Hex hex) {
        String message = "terrain : " + hex.getTerrain().name + "\n"
                + "feature : " + hex.getFeature().name + "\n"
                + "resource : " + hex.getResource().name + "\n"
                + "improvement : " + hex.getImprovement().name + "\n"
                + "percent of improvement = " + hex.getPercentOfBuildingImprovement();
        if (hex.getCivilUnit() != null)
            message += "\n" + "civil unit : " + hex.getCivilUnit().getName().getName();
        if (hex.getMilitaryUnit() != null)
            message += "\n" + "military unit : " + hex.getMilitaryUnit().getName().getName();
        if (hex.getCity() != null)
            message += "\n" + "city : " + hex.getCity().getName();
        if (hex.isHasRoad())
            message += "\n" + "has road";
        if (hex.doesHaveRiver())
            message += "\n" + "has river";
        return message;
    }

    private void addUnits(Hex hex, Group group) {
        if (hex.getCivilUnit() != null) {
            ImageView civilUnit = new ImageView(UnitName.valueOf(hex.getCivilUnit().getName().name()).getImage());
            civilUnit.setFitHeight(50);
            civilUnit.setFitWidth(50);
            civilUnit.setY(0);
            civilUnit.setX(75);
            group.getChildren().add(civilUnit);
            civilUnit.setOnMouseClicked(event -> {
                createPopupAndGlowForNode((String) Controller.send("selectCivilUnit", hex.getCoordinatesInArray().get('x'),
                        hex.getCoordinatesInArray().get('y')), civilUnit, true, true);
            });
        }
        if (hex.getMilitaryUnit() != null) {
            ImageView militaryUnit = new ImageView(UnitName.valueOf(hex.getMilitaryUnit().getName().name()).getImage());
            militaryUnit.setFitHeight(60);
            militaryUnit.setFitWidth(60);
            militaryUnit.setY(75);
            militaryUnit.setX(45);
            group.getChildren().add(militaryUnit);
            militaryUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    createPopupAndGlowForNode((String) Controller.send("selectMilitaryUnit", hex.getCoordinatesInArray().get('x'),
                            hex.getCoordinatesInArray().get('y')), militaryUnit, true, true);
                }
            });
        }
    }

    public void fillMap() {
        map.getChildren().clear();
        int j = 120;
        Controller.send("adjustVisibility");
        int maxX = ((Double) Controller.send("getNumberOfRows")).intValue();
        int maxY = ((Double) Controller.send("getNumberOfColumns")).intValue();
        for (int x = 0; x < maxX; x++) {
            int i = 100;
            for (int y = 0; y < maxY; y++) {
                Group hexView = graphicalHex(Controller.getHex(x,y));

                hexView.setLayoutX(i);

                if (y % 2 == 1)
                    hexView.setLayoutY(j + 72);
                else
                    hexView.setLayoutY(j);
                map.getChildren().add(hexView);
                i += 108;
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
                        Controller.send("discard", isUnit);
                    }
                }).start();
            }
        });
    }

    private void addOptions(boolean isUnit) {
        if (isUnit) {
            Button button = new Button("delete unit");
            button.setOnAction(event -> {
                String message = (String) Controller.send("deleteSelectedUnit");
                updateAll();
                createPopupAndGlowForNode(message, null, false, false);
            });
            popupVBox.getChildren().add(button);
            button = new Button("sleep unit");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = (String) Controller.send("sleepSelectedUnit");
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("wake unit");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = (String) Controller.send("wakeUpSelectedUnit");
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("cancel mission");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String message = (String) Controller.send("cancelSelectedUnitMission");
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                }
            });
            popupVBox.getChildren().add(button);
            Unit unit = Controller.getSelectedUnit();
            if (unit instanceof SettlerUnit) {

                button = new Button("found city");
                button.setOnAction(event -> {
                    String message = (String) Controller.send("foundCity");
                    updateAll();
                    createPopupAndGlowForNode(message, null, false, false);
                });
                popupVBox.getChildren().add(button);

            } else if (unit instanceof WorkerUnit) {
                button = new Button("remove jungle or swamp");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("removeJungleOrSwamp");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("remove route");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("removeRoute");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("repair");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("repair");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("build improvement");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Improvement> improvements = Controller.getGame().getSelectedCivilization().getOpenedImprovements();
                        popupVBox.getChildren().clear();
                        for (Improvement improvement : improvements) {
                            Button improvementButton = new Button(improvement.name);
                            improvementButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    String message = (String) Controller.send("buildImprovement", improvement.name);
                                    updateAll();
                                    createPopupAndGlowForNode(message, null, false, false);
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
                        String message = (String) Controller.send("alertSelectedUnit");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("fortify");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("fortifySelectedUnit");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("fortify till healed");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("fortifySelectedUnitTillHeal");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("garrison");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("garrisonSelectedUnit");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("set up");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("setupRangedSelectedUnit");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
                button = new Button("pillage");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String message = (String) Controller.send("pillage");
                        updateAll();
                        createPopupAndGlowForNode(message, null, false, false);
                    }
                });
                popupVBox.getChildren().add(button);
            }
        } else {
            Button button = new Button("choose unit");
            button.setOnAction(event -> {
                String message = (String) Controller.send("getAvailableUnitsInCity");
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else {
                    String[] units = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String unit : units) {
                        Button unitButton = new Button(unit);
                        unitButton.setOnAction(event1 -> createPopupAndGlowForNode((String) Controller.send
                                ("chooseProductionForUnit", unit), null, false, false));
                        popupVBox.getChildren().add(unitButton);
                    }
                }
            });
            popupVBox.getChildren().add(button);
            button = new Button("buy unit");
            button.setOnAction(event -> {
                String message = (String) Controller.send("getAvailableUnitsInCity");
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else {
                    String[] units = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String unit : units) {
                        Button unitButton = new Button(unit);
                        unitButton.setOnAction(event12 -> {
                            String message1 = (String) Controller.send("buyUnit", unit);
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
                String message = (String) Controller.send("getAvailableBuildingsForCity");
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else if (message.length() == 0)
                    createPopupAndGlowForNode("no building is possible now", null, false, false);
                else {
                    String[] buildings = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String building : buildings) {
                        Button unitButton = new Button(building.split(" :")[0]);
                        unitButton.setTooltip(new Tooltip(building));
                        unitButton.setOnAction(event12 -> {
                            String message1 = (String) Controller.send("buyBuilding", building.split(" :")[0]);
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
                String message = (String) Controller.send("getAvailableBuildingsForCity");
                if (message.startsWith("error"))
                    createPopupAndGlowForNode(message, null, false, false);
                else if (message.length() == 0)
                    createPopupAndGlowForNode("no building is possible now", null, false, false);
                else {
                    String[] buildings = message.split("\n");
                    popupVBox.getChildren().clear();
                    for (String building : buildings) {
                        Button unitButton = new Button(building.split(" :")[0]);
                        unitButton.setTooltip(new Tooltip(building));
                        unitButton.setOnAction(event12 -> {
                            String message1 = (String) Controller.send("buildBuilding", building.split(" :")[0]);
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
        Controller.send("change menu GameSetting");
        window.setScene(Controller.getGameSettingsMenu().getScene());
    }

    public void openTechnologyTree(MouseEvent mouseEvent) {

        window.setScene(Controller.getTechnologyTree().getScene());
    }

    @FXML
    public Popup popup = new Popup();

    public void cityPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        String message = (String) Controller.send("showCitiesPanel");
        message = message.trim();
        String[] units = message.split("\n");
        for (String unit : units) {
            Label label = new Label(unit);
            label.setStyle("-fx-background-color: white");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popup.hide();
                    createCityPage((String) Controller.send("selectCity", Integer.parseInt(unit.substring(0, 1))));
                }
            });
            popup.getContent().add(label);
        }
        popup.setX(window.getX() + 387);
        popup.setY(window.getY() + 115);
        popup.setAutoHide(true);
        popup.show(window);
    }

    private String cityNames() {
        String message = "";
        if (Controller.getGame().getSelectedCivilization().getCities().size() == 0)
            return "no city";
        else {
            for (City city : Controller.getGame().getSelectedCivilization().getCities()) {
                message += city.getName() + "\n";
            }
            return message;
        }
    }

    public void unitPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        String message = (String) Controller.send("showUnitsPanel");
        message = message.trim();
        String[] units = message.split("\n");
        for (String unit : units) {
            Label label = new Label(unit);
            label.setStyle("-fx-background-color: white");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popup.hide();
                    createPopupAndGlowForNode((String) Controller.send("selectUnit", Integer.parseInt(unit.substring(0, 1)))
                            , null, true, true);
                }
            });
            popup.getContent().add(label);
        }
        popup.setX(window.getX() + 410);
        popup.setY(window.getY() + 115);
        popup.setAutoHide(true);
        popup.show(window);
    }


    public synchronized void nextTurn() {
        String message = (String) Controller.send("changeTurn", false);
        createPopupAndGlowForNode(message, null, false, false);
        if (message.startsWith("congratulations")) {
            scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Controller.send("clean");
                    Controller.send("change menu GameSetting");
                    Controller.setGameSettingsMenu(new GameSettingsMenu());
                    window.setScene(Controller.getGameSettingsMenu().getScene());
                }
            });
        }
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

    public String notif() {
        String message = "<<Notification History>>\n";
        ArrayList<String> allMessages = new ArrayList<>(); //////////////////////////////////////Controller.getNotificationHistory();
        for (String allMessage : allMessages) {
            message += allMessage + "\n";
        }
        return message;
    }

    public void demographicPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        popupLabel.setText((String) Controller.send("showDemographicsPanel"));
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
        Civilization nowCivilization = Controller.getGame().getSelectedCivilization();
        for (Civilization civilization : Controller.getGame().getCivilizations()) {
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
                        createPopupAndGlowForNode((String) Controller.send("piece", civilization.getUser().getNickname()), null, false, false);
                    } else {
                        createPopupAndGlowForNode((String) Controller.send("declareWar", civilization.getUser().getNickname()), null, false, false);
                    }
                    popup.hide();
                }
            });

            trade.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    popup.getContent().clear();

                    vBox.getChildren().clear();
                    vBox.setSpacing(10);
                    vBox.prefHeight(400);
                    vBox.prefWidth(200);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setStyle("-fx-border-color: green;");
                    vBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    Label label1 = new Label("own resources");
                    label1.setTextFill(Color.WHITE);
                    vBox.getChildren().add(label1);
                    ChoiceBox<String> choiceBox = new ChoiceBox<String>();
                    for (Resource resource : Controller.getGame().getSelectedCivilization().getStrategicResources()) {
                        choiceBox.getItems().add(resource.name);
                    }
                    for (Resource resource : Controller.getGame().getSelectedCivilization().getLuxuryResources()) {
                        choiceBox.getItems().add(resource.name);
                    }
                    Label label2 = new Label("other resources");
                    label2.setTextFill(Color.WHITE);
                    ChoiceBox<String> choiceBox1 = new ChoiceBox<>();

                    for (Resource openedResource : civilization.getStrategicResources()) {
                        choiceBox1.getItems().add(openedResource.name);
                    }
                    for (Resource openedResource : civilization.getLuxuryResources()) {
                        choiceBox1.getItems().add(openedResource.name);
                    }

                    Label label3 = new Label("Gold you give :");
                    label3.setTextFill(Color.WHITE);
                    TextField goldYouLoss = new TextField("Gold...");
                    Label label4 = new Label("Gold you want :");
                    label4.setTextFill(Color.WHITE);
                    TextField goldYouGet = new TextField("Gold...");
                    Button button = new Button("TRADE");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            createPopupAndGlowForNode("accept deal?", null, false, false);
                            Button reject = new Button("reject");
                            reject.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    createPopupAndGlowForNode("ok", null, false, false);
                                    popup.hide();
                                }
                            });
                            popupVBox.getChildren().add(reject);
                            Button accept = new Button("accept");
                            accept.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    String message = (String) Controller.send("trade", choiceBox1.getValue(), choiceBox.getValue(),
                                            goldYouGet.getText(), goldYouLoss.getText(), civilization.getUsername());
                                    updateAll();
                                    createPopupAndGlowForNode(message, null, false, false);
                                    popup.hide();

                                }
                            });
                            popupVBox.getChildren().add(accept);
                        }
                    });
                    button.setStyle("-fx-base: green;");
                    vBox.getChildren().add(choiceBox);
                    vBox.getChildren().add(label2);
                    vBox.getChildren().add(choiceBox1);
                    vBox.getChildren().add(label3);
                    vBox.getChildren().add(goldYouLoss);
                    vBox.getChildren().add(label4);
                    vBox.getChildren().add(goldYouGet);
                    vBox.getChildren().add(button);
                    popup.hide();
                    popup.getContent().add(vBox);
                    vBox.setVisible(true);
                    popup.show(window);
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
                    result = (String) Controller.send("cheatIncreaseTurn", amount);
                    break;
                case "gold":
                    result = (String) Controller.send("cheatIncreaseGold", amount);
                    break;
                case "science":
                    result = (String) Controller.send("cheatIncreaseScience", amount);
                    break;
                case "citizens":
                    result = (String) Controller.send("cheatIncreaseCitizens", amount);
                    break;
                case "score":
                    result = (String) Controller.send("cheatIncreaseScore", amount);
                    break;
            }
        } else if (command.equals("cheat open all technologies")) {
            result = (String) Controller.send("cheatOpenAllTechnologies");
        } else if (command.equals("cheat make the whole map visible")) {
            result = (String) Controller.send("cheatMakeMapDetermined");
        } else if (command.equals("cheat win")) {
            result = (String) Controller.send("cheatWin");
        } else if ((matcher = getMatcher(command, "^cheat found city on (?<x>\\d+) (?<y>\\d+)$")) != null) {
            result = (String) Controller.send("cheatFoundCityOn", Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        } else if ((command.equals("cheat increase health of selected unit$"))) {
            result = (String) Controller.send("cheatIncreaseHealthOfSelectedUnit");
        } else if ((command.equals("cheat increase power of selected unit"))) {
            result = (String) Controller.send("cheatIncreasePowerOfSelectedUnit");
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
        button.setOnAction(event -> popup.hide());


        Button button2 = new Button("menu");
        button2.setStyle("-fx-base: red;");
        button2.setOnAction(event -> {
            popup.hide();
            Controller.send("clean");
            Controller.send("change menu GameSetting");
            Controller.setGameSettingsMenu(new GameSettingsMenu());
            window.setScene(Controller.getGameSettingsMenu().getScene());
        });
        popupVBox.getChildren().add(button);
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
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            canChangePopup = true;
        }).start();
    }

    public void openMilitaryPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        String message = (String) Controller.send("showUnitsPanel");
        message = message.trim();
        String[] units = message.split("\n");
        for (String unit : units) {
            Label label = new Label(unit);
            label.setStyle("-fx-background-color: white");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popup.hide();
                    createPopupAndGlowForNode((String) Controller.send("selectUnit", Integer.parseInt(unit.substring(0, 1)))
                            , null, true, true);
                }
            });
            popup.getContent().add(label);
        }
        popup.setX(window.getX() + 625);
        popup.setY(window.getY() + 115);
        popup.setAutoHide(true);
        popup.show(window);
    }

    public void openEconomicPanel(MouseEvent mouseEvent) {
        popup.getContent().clear();
        String message = (String) Controller.send("showCitiesPanel");
        message = message.trim();
        String[] units = message.split("\n");
        for (String unit : units) {
            Label label = new Label(unit);
            label.setStyle("-fx-background-color: white");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popup.hide();
                    createCityPage((String) Controller.send("selectCity", Integer.parseInt(unit.substring(0, 1))));
                }
            });
            popup.getContent().add(label);
        }
        popup.setX(window.getX() + 670);
        popup.setY(window.getY() + 115);
        popup.setAutoHide(true);
        popup.show(window);
    }
}
