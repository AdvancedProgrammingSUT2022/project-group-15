package client.view;

import client.controller.Controller;
import client.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.model.GlobalThings;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameSettingsMenu extends Menu implements Initializable {



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
    private ChoiceBox<String> loadGame;
    @FXML
    private ChoiceBox<Integer> numberOfKeptSavedFiles;
    @FXML
    private ImageView musicOn;
    @FXML
    private ImageView musicOff;

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
        musicOff.visibleProperty().bind(GlobalThings.musicOnProperty().not());
        musicOff.disableProperty().bind(GlobalThings.musicOnProperty());
        musicOn.visibleProperty().bind(GlobalThings.musicOnProperty());
        musicOn.disableProperty().bind(GlobalThings.musicOnProperty().not());
        numberOfPlayersBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10);
        mapWidth.getItems().addAll(6, 8, 10, 12, 14, 16, 18, 20);
        mapWidth.setValue(10);
        mapLength.getItems().addAll(6, 8, 10, 12, 14, 16, 18, 20);
        mapLength.setValue(10);
        autoSave.getItems().addAll(0, 3, 5, 7, 10);
        autoSave.setValue(0);
        numberOfKeptSavedFiles.getItems().addAll(1, 2, 3, 4, 5);
        numberOfKeptSavedFiles.setValue(1);
        addSavedGames();
        addToolTips();
        String ans = (String) Controller.send("inviteFriend",Controller.getMyUser().getUsername(),createArraylistOfUsers());
        Label label = new Label(Controller.getMyUser().getUsername());
        friendsInGame.getChildren().add(label);

    }

    private void addSavedGames() {
        File folder = new File("./src/main/resources/savedGames");
        File[] listOfFiles = folder.listFiles();

        loadGame.getItems().add("none");
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().startsWith("dont"))
                continue;
            if (listOfFiles[i].isFile()) {
                loadGame.getItems().add(listOfFiles[i].getName());
            }
        }
        loadGame.setValue("none");

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
        Controller.send("change menu Main");
    }


    public void cancel(MouseEvent mouseEvent) {
        // TODO: 6/29/2022 phase3
    }


    public void inviteFriend(MouseEvent mouseEvent) {
        String out = (String) Controller.send("inviteFriend",usernameTextField.getText(),createArraylistOfUsers());
        if (out.equals("done")){
            Label label = new Label(usernameTextField.getText());
            friendsInGame.getChildren().add(label);
        }
        information.setText(out);
        usernameTextField.clear();
    }


    public void startGameWithFriend(MouseEvent mouseEvent) {
        if (loadGame.getValue().equals("none")) {
            String text = (String) Controller.send("gameWithFriends",createArraylistOfUsers(),mapLength.getValue(),mapWidth.getValue(),
                    autoSave.getValue(), numberOfKeptSavedFiles.getValue());
            information.setText(text);
            if (text.startsWith("a new game started with ")) {
                Controller.send("change menu Game");
                setup(cancelButton);
                Controller.setGameMenu(new GameMenu());
                window.setScene(Controller.getGameMenu().getScene());

            }
        } else {
            String text = (String) Controller.send("loadSavedGame",loadGame.getValue());
            if (text.endsWith("successfully")) {
                setup(cancelButton);
                Controller.send("change menu Game");
                Controller.setGameMenu(new GameMenu());
                window.setScene(Controller.getGameMenu().getScene());

            }

        }
    }

    public void findGame(MouseEvent mouseEvent) throws InterruptedException {
        String out = (String) Controller.send("findGame",numberOfPlayersBox);

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
//                HashMap<Integer, String> hashMap = new HashMap<>();
//                hashMap.put(1, User.getLoggedInUser().getUsername());
//                for (int i = 2; i <= numberOfPlayersBox.getValue(); i++) {
//                    if (User.getUsers().get(i - 2) != User.getLoggedInUser())
//                        hashMap.put(i, User.getUsers().get(i - 2).getUsername());
//                    else
//                        hashMap.put(i, User.getUsers().get(User.getUsers().size() - 1).getUsername());
//                }
                // TODO: 7/10/2022 phase 3

            }
        }).start();

    }

    public void mute() {
        GlobalThings.pauseMusic();
    }

    public void unmute() {
        GlobalThings.playMusic();
    }
    private ArrayList<String> createArraylistOfUsers(){
        ArrayList<String> ans = new ArrayList<>();
        for (Node child : friendsInGame.getChildren()) {
            ans.add (((Label) child).getText());
        }
        return ans;
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