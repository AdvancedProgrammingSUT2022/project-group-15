package server.controller;

import com.google.gson.Gson;
import server.model.GlobalThings;
import server.model.Request;
import server.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.time.LocalDateTime;

import static server.model.GlobalThings.gson;

public class CommandSender{
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    public CommandSender(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendCommand(Response response){
        try {
            String s = dataInputStream.readUTF();
            dataOutputStream.writeUTF(gson.toJson(response));
            dataOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
