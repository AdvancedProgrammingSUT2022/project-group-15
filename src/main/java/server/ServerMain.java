package server;

import server.controller.SocketHandler;
import server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerMain {
    private LinkedList<SocketHandler> socketHandlers = new LinkedList<>();
    private ArrayList<User> players = new ArrayList<User>();
    private LinkedList<SocketHandler> socketHandlersPlaying = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(13000);
        System.out.println("server listening...");
        while (true) {
            SocketHandler socketHandler = new SocketHandler(serverSocket.accept());
            System.out.println("New connection made");
            socketHandler.start();
        }
    }
}
