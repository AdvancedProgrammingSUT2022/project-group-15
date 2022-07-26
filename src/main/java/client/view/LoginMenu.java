package client.view;

import client.controller.Controller;

import client.model.Response;
import client.model.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import server.controller.SocketHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu extends Menu implements Initializable {


    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField nickname;
    @FXML
    private ImageView forbiddenIcon;
    @FXML
    private ImageView checkIcon;
    @FXML
    private Text message;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkIcon.setVisible(false);
        forbiddenIcon.setVisible(false);
        message.setText("");

        username.setOnKeyReleased(event -> {
            if (username.getText().matches("^\\w+.*")) {
                username.setStyle("-fx-border-color: green");
            } else if (username.getText().length() != 0) {
                username.setStyle("-fx-border-color: red");
            } else {
                username.setStyle("-fx-border-color: #c948fa;");
            }
        });

        password.setOnKeyReleased(event -> {
            if ((boolean) Controller.send("isStrong", password.getText())) {
                password.setStyle("-fx-border-color: green");
            } else if (password.getText().length() != 0) {
                password.setStyle("-fx-border-color: red");
            } else {
                password.setStyle("-fx-border-color: #c948fa;");
            }
        });
    }

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/loginMenu.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void login() {
        setup(username);
        String loginResult = ((String) Controller.send("login", username.getText(), password.getText()));
        if (!loginResult.startsWith("user logged")) {
            message.setFill(Paint.valueOf("red"));
            forbiddenIcon.setVisible(true);
            message.setText(loginResult);
        } else {
            authToken = loginResult.substring(28);
            forbiddenIcon.setVisible(false);
            checkIcon.setVisible(true);
            message.setOpacity(1);
            checkIcon.setOpacity(1);
            message.setFill(Paint.valueOf("green"));
            message.setText("Login successful! Wait...");
            nextPage();
        }
    }

    public void signUp() {
        setup(username);
        String signupResult = ((String) Controller.send("signUp", username.getText(), password.getText(), nickname.getText()));
        if (!signupResult.startsWith("user created")) {
            message.setFill(Paint.valueOf("red"));
            forbiddenIcon.setVisible(true);
            message.setText(signupResult);
        } else {
            authToken = signupResult.substring(27);
            forbiddenIcon.setVisible(false);
            checkIcon.setVisible(true);
            message.setOpacity(1);
            checkIcon.setOpacity(1);
            message.setFill(Paint.valueOf("green"));
            message.setText("SignUp successful! Wait...");
            nextPage();
        }
    }

    private void nextPage() {
        FadeTransition messageFade = new FadeTransition(Duration.millis(2000), message);
        messageFade.setByValue(1);
        messageFade.setToValue(0);
        FadeTransition checkIconFade = new FadeTransition(Duration.millis(2000), checkIcon);
        checkIconFade.setByValue(1);
        checkIconFade.setToValue(0);
        messageFade.setOnFinished(e -> {
            window.setScene(Controller.getMainMenu().getScene());
        });
        checkIconFade.play();
        messageFade.play();
        Controller.send("change menu Main");
        User.setLoggedInUser(Controller.getUser(username.getText()));
    }
}