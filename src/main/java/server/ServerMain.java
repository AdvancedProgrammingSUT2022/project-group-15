package server;

import server.controller.ServerController;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        ServerController.getInstance().run();
    }
}
