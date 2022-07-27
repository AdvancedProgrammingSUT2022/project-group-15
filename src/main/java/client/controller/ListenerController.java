package client.controller;

import client.model.GlobalThings;
import client.model.Request;
import client.model.Response;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ListenerController extends Thread {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ListenerController() {
        this.setDaemon(true);
        try {
            Socket socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            while (true) {
                Gson gson = GlobalThings.gson;
                dataOutputStream.writeUTF(gson.toJson(new Request()));
                dataOutputStream.flush();
                System.out.println("Waiting for command from server");
                Response response = gson.fromJson(dataInputStream.readUTF(), Response.class);
                System.out.println("command from server received");
                handel(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handel(Response response) {
        String command = (String) response.getAnswer();
        if (command.startsWith("invite from")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    HBox hBox = new HBox();
                    hBox.setStyle("-fx-background-color: purple");
                    Label label = new Label(command + " do you accept?");
                    label.setFont(new Font(40));
                    label.setStyle("-fx-fill: yellow");
                    Button accept = new Button("accept");
                    accept.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                        }
                    });
                    Button reject = new Button("reject");
                    reject.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Controller.send("reject invite from " + command.substring(12));
                            ((AnchorPane) Controller.getWindow().getScene().getRoot()).getChildren().remove(hBox);
                        }
                    });

                    hBox.getChildren().add(label);
                    hBox.getChildren().add(accept);
                    hBox.getChildren().add(reject);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setPrefHeight(720);
                    hBox.setPrefWidth(1280);
                    ((AnchorPane) Controller.getWindow().getScene().getRoot()).getChildren().add(hBox);
                }
            });
            return;
        }

        if (command.endsWith("has rejected your invite")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ((Label)((AnchorPane) Controller.getWindow().getScene().getRoot()).getChildren().get(9)).setText(command);
                }
            });
            return;
        }
        switch (command) {


        }
    }
}
