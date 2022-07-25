package client.view;

import client.controller.Controller;
import client.enums.Avatar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenu extends Menu implements Initializable {


    @FXML
    private ListView<ImageView> listOfAvatars;
    @FXML
    private ImageView avatar;
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorText;
    @FXML
    private Label password;
    @FXML
    private Label username;
    @FXML
    private Label nickname;
    @FXML
    private ImageView deleteAccount;
    @FXML
    private ImageView logout;
    @FXML
    private Button saveChangesButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip.install(deleteAccount, new Tooltip("Delete Account"));
        Tooltip.install(logout, new Tooltip("Logout"));
        for (Avatar avatar : Avatar.values()) {
            listOfAvatars.getItems().add(new ImageView(avatar.image));
        }
//        avatar.imageProperty().bind(User.getLoggedInUser().avatarProperty());
//        password.textProperty().bind(User.getLoggedInUser().passwordProperty());
//        username.textProperty().bind(User.getLoggedInUser().usernameProperty());
//        nickname.textProperty().bind(User.getLoggedInUser().nicknameProperty());
        saveChangesButton.setDisable(true);

        passwordField.setOnKeyReleased(event -> {
            if ((boolean)Controller.send("isStrong",passwordField.getText())) {
                passwordField.setStyle("-fx-border-color: green");
            } else if (passwordField.getText().length() != 0) {
                passwordField.setStyle("-fx-border-color: red");
            } else {
                passwordField.setStyle("-fx-border-color: #c948fa;");
            }
            saveChangesButton.setDisable(passwordField.getText().isEmpty() && nicknameField.getText().isEmpty());
        });

        nicknameField.setOnKeyReleased(event -> {
            if (nicknameField.getText().matches("^\\w+.*")) {
                nicknameField.setStyle("-fx-border-color: green");
            } else if (nicknameField.getText().length() != 0) {
                nicknameField.setStyle("-fx-border-color: red");
            } else {
                nicknameField.setStyle("-fx-border-color: #c948fa;");
            }
            saveChangesButton.setDisable(passwordField.getText().isEmpty() && nicknameField.getText().isEmpty());
        });
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/profileMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void openMainMenu() {
        setup(avatar);
        window.setScene(Controller.getMainMenu().getScene());
    }

    public void deleteAccount() {
        Controller.send("deleteCurrentPlayerAccount");
        logout();
    }

    public void logout() {
        Controller.send("logout");
        setup(avatar);
        Controller.getLoginMenu().setScene(null);
        window.setScene(Controller.getLoginMenu().getScene());
        Controller.getMainMenu().setScene(null);
        Controller.getProfileMenu().setScene(null);
    }

    public void submitChanges() {
        String changeResult = (String) Controller.send("submitChanges",nicknameField.getText(), passwordField.getText());
        if (changeResult.equals("changes submitted")) {
            errorText.setOpacity(0);
            passwordField.clear();
            nicknameField.clear();
            nicknameField.setStyle("-fx-border-color: #c948fa;");
            passwordField.setStyle("-fx-border-color: #c948fa;");
        } else {
            errorText.setText(changeResult.toString());
            errorText.setOpacity(1);
        }
        saveChangesButton.setDisable(passwordField.getText().isEmpty() && nicknameField.getText().isEmpty());
    }

    public void changeAvatar() {
//        User.getLoggedInUser().setAvatar(Avatar.values()[listOfAvatars.getSelectionModel().getSelectedIndex()]);
//        User.saveUsers();
        saveChangesButton.setDisable(passwordField.getText().isEmpty() && nicknameField.getText().isEmpty());
    }
}
