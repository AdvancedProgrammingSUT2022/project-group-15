package view;

import controller.Controller;
import controller.GameSettingMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.User;
import sun.security.util.Length;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameSettingsMenu extends Menu implements Initializable {

    private final GameSettingMenuController controller = new GameSettingMenuController();

    @FXML
    public Button cancelButton;
    @FXML
    public VBox friendsInGame;
    @FXML
    public Label information;
    @FXML
    public TextField usernameTextField;
    @FXML
    public ChoiceBox<Integer> numberOfPlayersBox;
    public Button findGameButton;
    public Button startGameButton;
    public ChoiceBox<Integer> mapWidth;
    public ChoiceBox<Integer> mapLength;
    public ChoiceBox<Integer> autoSave;
    public ChoiceBox loadGame;


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

        // TODO: 7/10/2022 load saved games

        controller.inviteFriend(User.getLoggedInUser().getUsername(), friendsInGame);
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
        information.setText(controller.gameWithFriends(friendsInGame, mapLength.getValue(),mapWidth.getValue(),autoSave.getValue()));

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