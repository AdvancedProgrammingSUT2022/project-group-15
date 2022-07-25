package server;

import server.controller.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
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
