package client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class PublicChat extends Menu {
    @FXML
    public TextField textField = new TextField();
    @FXML
    public VBox chatBox;
    @FXML
    public Button sendButton;
    @FXML
    public Button backButton;

    @Override
    public Scene getScene() {
        if (scene == null) {
            try {
                AnchorPane root = FXMLLoader.load(new URL(this.getClass().getResource("/fxml/PublicChat.fxml").toExternalForm()));
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scene;
    }

    public void send() {
        String message = null;
        if (textField.getText().length() > 0) {
            message = textField.getText();
        }
        System.out.println(message);
    }

   /* private void addMsgInfo() {
        String sender;
        if (User.getLoggedInUser() == null) sender = "Null";
        else
            sender = User.getLoggedInUser().getUsername();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = formatter.format(date).toString().substring(11, 16);
        String info = sender + " " + time + "  ";
        Text text = new Text(info);
        text.setTextAlignment(TextAlignment.valueOf("CENTER"));
        text.setStyle("-fx-font-family: 'Tw Cen MT'; -fx-font-size: 17;");
        HBox hBox = new HBox(text);
        hBox.setMaxWidth(text.getLayoutBounds().getWidth() + 30);
        hBox.setStyle("-fx-background-color: lightBlue; -fx-background-radius: 0 0 7 7;");
        hBox.setTranslateX(20);
        hBox.setTranslateY(chatBox.getChildren().get(chatBox.getChildren().size() - 1).getTranslateY() +
                chatBox.getChildren().get(chatBox.getChildren().size() - 1).getLayoutBounds().getHeight() - 10);
        //
        Circle circle = new Circle();
        circle.setRadius(5);
        circle.setStyle("-fx-fill: orange;");
        circle.setTranslateY(hBox.getLayoutY() + hBox.getLayoutBounds().getHeight() / 2 + 5);
        hBox.getChildren().add(circle);
        chatBox.getChildren().add(hBox);
        PublicChats.getInstance().setChatBox(this.chatBox);
    }

    public void type(KeyEvent event) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 70) {
                    textField.setText(oldValue);
                } else {
                    textField.setText(newValue);
                }
            }
        });

    }

    public void back(MouseEvent mouseEvent) {
        Main.clickSound();
        NavigationTransition.fadeTransition(backButton, "MainMenu");
    }
}*/
}