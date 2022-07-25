package server.controller;


import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import server.model.Request;
import server.model.Response;
import server.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SocketHandler extends Thread {


    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String menu = "Login";

    private GameMenuController gameMenuController;
    private GameSettingMenuController gameSettingMenuController;
    private LoginMenuController loginMenuController = new LoginMenuController();
    private MainMenuController mainMenuController;
    private ProfileMenuController profileMenuController;
    private ScoreBoardController scoreBoardController;

    public SocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {

                Gson gson = new Gson();
                String s = dataInputStream.readUTF();
                Request request = gson.fromJson(s, Request.class);
                System.out.println("New request from " + socket);
                Response response = handleRequest(request);
                dataOutputStream.writeUTF(gson.toJson(response));
                dataOutputStream.flush();
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
            ignored.printStackTrace();
        }
    }

    private Response handleRequest(Request request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = request.getMethodName();
        if (methodName.startsWith("change menu")) {
            changeMenu(methodName.substring(12));
            return new Response();
        }
        if (methodName.equals("getUser")){
            Response response = new Response();
            response.setAnswer(User.getLoggedInUser().toJson());
            return response;
        }
        Class<?>[] types = new Class[request.getParameters().size()];
        for (int i = 0; i < request.getParameters().size(); i++) {
            types[i] = request.getParameters().get(i).getClass();
        }

        Object[] arguments = new Object[request.getParameters().size()];
        arguments = request.getParameters().toArray(arguments);
        Method method;
        Object answer = null;
        switch (menu) {
            case "Game":
                method = gameMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(gameMenuController, arguments);
                break;
            case "GameSetting":
                method = gameSettingMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(gameSettingMenuController, arguments);
                break;
            case "Login":
                method = loginMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(loginMenuController, arguments);
                break;
            case "Main":
                method = mainMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(mainMenuController, arguments);
                break;
            case "Profile":
                method = profileMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(profileMenuController, arguments);
                break;
            case "ScoreBoard":
                method = scoreBoardController.getClass().getMethod(methodName, types);
                answer = method.invoke(scoreBoardController, arguments);
                break;
        }
        Response response = new Response();
        response.setAnswer(answer);
        return response;
    }

    private void changeMenu(String name) {
        menu = name;
        System.out.println("you are now in " + name);
        switch (menu) {
            case "Game":
                gameMenuController = new GameMenuController();
                break;
            case "GameSetting":
                gameSettingMenuController = new GameSettingMenuController();
                break;
            case "Login":
                loginMenuController = new LoginMenuController();
                break;
            case "Main":
                mainMenuController = new MainMenuController();
                break;
            case "Profile":
                profileMenuController = new ProfileMenuController();
                break;
            case "ScoreBoard":
                scoreBoardController = new ScoreBoardController();
                break;
        }
    }


}
