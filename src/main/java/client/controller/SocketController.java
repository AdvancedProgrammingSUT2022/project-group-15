package client.controller;

import client.model.Request;
import client.model.Response;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketController {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketController() {
        try {
            this.socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response send(Request request) {
        try {

            dataOutputStream.writeUTF(new Gson().toJson(request));
            dataOutputStream.flush();
            System.out.println("Waiting for response");
            Response response = new Gson().fromJson(dataInputStream.readUTF(), Response.class);
            System.out.println("response received");
            return response;
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

}
