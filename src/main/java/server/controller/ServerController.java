package server.controller;

import server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerController {

    private static ServerController serverController=null;
    private ServerController(){

    }
    public static ServerController getInstance(){
        if (serverController==null)
            serverController=new ServerController();
        return serverController;
    }
    private ArrayList<SocketHandler> socketHandlers = new ArrayList<>();
    private ArrayList<SocketHandler> socketHandlersPlaying = new ArrayList<>();

    public ArrayList<SocketHandler> getSocketHandlers() {
        return socketHandlers;
    }

    public ArrayList<SocketHandler> getSocketHandlersPlaying() {
        return socketHandlersPlaying;
    }

    public void run() throws IOException {
        User.loadUsers();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("exit")) {
                User.saveUsers();
                System.exit(0);
            }
        }).start();
        ServerSocket serverSocket = new ServerSocket(13000);
        System.out.println("server listening...");
        while (true) {
            SocketHandler socketHandler = new SocketHandler(serverSocket.accept());
            System.out.println("New connection made");
            socketHandler.start();
            CommandSender commandSender = new CommandSender(serverSocket.accept());
            socketHandler.setCommandSender(commandSender);
            socketHandlers.add(socketHandler);
        }
    }

    public void removeSocket(SocketHandler socketHandler) {
        socketHandlers.remove(socketHandler);
    }
}
