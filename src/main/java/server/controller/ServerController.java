package server.controller;

import server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerController {

    private ArrayList<SocketHandler> socketHandlers = new ArrayList<>();
    private ArrayList<SocketHandler> socketHandlersPlaying = new ArrayList<>();


    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(13000);
        System.out.println("server listening...");
        while (true) {
            SocketHandler socketHandler = new SocketHandler(serverSocket.accept(),this);
            System.out.println("New connection made");
            socketHandler.start();
            socketHandlers.add(socketHandler);
        }
    }


    public void removeSocket(SocketHandler socketHandler) {
        socketHandlers.remove(socketHandler);
    }
}
