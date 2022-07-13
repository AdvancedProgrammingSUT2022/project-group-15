package view;

import controller.Controller;
import controller.GameSettingMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameSettingsMenu extends Menu implements Initializable {

    private final GameSettingMenuController controller = new GameSettingMenuController();

    @FXML
    private Button cancelButton;
    @FXML
    private VBox friendsInGame;
    @FXML
    private Label information;
    @FXML
    private TextField usernameTextField;
    @FXML
    private ChoiceBox<Integer> numberOfPlayersBox;
    @FXML
    private Button findGameButton;
    @FXML
    private Button startGameButton;
    @FXML
    private ChoiceBox<Integer> mapWidth;
    @FXML
    private ChoiceBox<Integer> mapLength;
    @FXML
    private ChoiceBox<Integer> autoSave;
    @FXML
    private ChoiceBox loadGame;
    @FXML
    private ChoiceBox<Integer> numberOfKeptSavedFiles;


    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/gameSettingsMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfPlayersBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10);
        mapWidth.getItems().addAll(6,8,10,12,14,16,18,20);
        mapWidth.setValue(10);
        mapLength.getItems().addAll(6,8,10,12,14,16,18,20);
        mapLength.setValue(10);
        autoSave.getItems().addAll(0,3,5,7,10);
        autoSave.setValue(0);
        numberOfKeptSavedFiles.getItems().addAll(1,2,3,4,5);
        numberOfKeptSavedFiles.setValue(1);
        // TODO: 7/10/2022 load saved games
        addToolTips();
        controller.inviteFriend(User.getLoggedInUser().getUsername(), friendsInGame);
    }

    private void addToolTips() {
        numberOfPlayersBox.setTooltip(new Tooltip("number of players in the game that we will find for you"));
        mapWidth.setTooltip(new Tooltip("number of tiles in the width"));
        mapLength.setTooltip(new Tooltip("number of tiles in the length"));
        autoSave.setTooltip(new Tooltip("automatically save the game after X moves"));
        loadGame.setTooltip(new Tooltip("load a game from disk (after choosing click start game)"));
        numberOfKeptSavedFiles.setTooltip(new Tooltip("maximum number of saves to be saved on disk automatically (old saves will be replaced)"));
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        setup(cancelButton);
        window.setScene(Controller.getMainMenu().getScene());
    }


    public void cancel(MouseEvent mouseEvent) {
        // TODO: 6/29/2022 phase3
    }


    public void inviteFriend(MouseEvent mouseEvent) {
        String out = controller.inviteFriend(usernameTextField.getCharacters().toString(), friendsInGame);
        information.setText(out);
    }


    public void startGameWithFriend(MouseEvent mouseEvent) {
        information.setText(controller.gameWithFriends(friendsInGame, mapLength.getValue(),mapWidth.getValue(),
                autoSave.getValue(), numberOfKeptSavedFiles.getValue()));
        // TODO: 7/11/2022 start game
        setup(cancelButton);
        window.setScene(Controller.getGameMenu().getScene());
    }

    public void findGame(MouseEvent mouseEvent) throws InterruptedException {
        String out = controller.findGame(numberOfPlayersBox);
        information.setText(out);
        if (out.startsWith("error"))
            return;
        cancelButton.setVisible(!cancelButton.isVisible());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cancelButton.setVisible(!cancelButton.isVisible());
                HashMap<Integer, String> hashMap = new HashMap<>();
                hashMap.put(1, User.getLoggedInUser().getUsername());
                for (int i = 2; i <= numberOfPlayersBox.getValue(); i++) {
                    if (User.getUsers().get(i - 2) != User.getLoggedInUser())
                        hashMap.put(i, User.getUsers().get(i - 2).getUsername());
                    else
                        hashMap.put(i, User.getUsers().get(User.getUsers().size()-1).getUsername());
                }
                // TODO: 7/10/2022 phase 3

            }
        }).start();

    }
}
//else if (command.startsWith("play game ")) {
//        HashMap<Integer, String> usernames = new HashMap<>();
//        matcher = Pattern.compile("(?:-p|--player)(?<number>\\d) (?<username>\\S+)").matcher(command);
//        while (matcher.find()) {
//        usernames.put(Integer.parseInt(matcher.group("number")), matcher.group("username"));
//        }
//        if (usernames.size() <= 1) {
//        System.out.println("not enough players");
//        } else {
//        System.out.println(controller.startGame(usernames));
//        System.out.println("you are in the game menu");
//        return "game menu";
//        }
//}