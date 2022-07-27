package client.controller;

import client.model.GlobalThings;
import client.model.Request;
import client.model.Response;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ListenerController extends Thread{

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ListenerController(){
        this.setDaemon(true);
        try {
            Socket socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handel(Response response) {
        String command = (String) response.getAnswer();
        if (command.startsWith("invite from")){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    HBox hBox = new HBox();
                    hBox.setStyle("-fx-background-color: purple");
                    Label label = new Label(command + " do you accept");
                    label.setFont(new Font(40));
                    label.setStyle("-fx-fill: yellow");
                    hBox.getChildren().add(label);
                    hBox.setAlignment(Pos.CENTER);
                    ((AnchorPane)Controller.getWindow().getScene().getRoot()).getChildren().add(hBox);
                }
            });
        }
        switch (command){


        }
    }
}
