package server.controller;


import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import server.model.Game;
import server.model.Request;
import server.model.Response;
import server.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SocketHandler extends Thread {
    private static final ArrayList<SocketHandler> onlinePlayers = new ArrayList<>();

    private User user = null;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private final XStream xStream = new XStream();

    private Gson gson = new Gson();

    private String menu = "Login";

    private GameMenuController gameMenuController;
    private GameSettingMenuController gameSettingMenuController;
    private LoginMenuController loginMenuController = new LoginMenuController();
    private MainMenuController mainMenuController;
    private ProfileMenuController profileMenuController;
    private ScoreBoardController scoreBoardController;

    public SocketHandler(Socket socket) throws IOException {
        onlinePlayers.add(this);
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public static ArrayList<SocketHandler> getOnlinePlayers() {
        return onlinePlayers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String s = dataInputStream.readUTF();
                Request request = gson.fromJson(s, Request.class);
                System.out.println("New request from " + socket);
                Response response = handleRequest(request);
                dataOutputStream.writeUTF(gson.toJson(response));
                dataOutputStream.flush();
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
            user.setLastOnlineTime(LocalDateTime.now());
            onlinePlayers.remove(this);
            // TODO : send updated list of users to online users
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
        if (methodName.equals("getGame")){
            Response response = new Response();
            response.setAnswer(xStream.toXML(Game.getGame()));
            return response;
        }
        if (methodName.equals("getHex")){
            Response response = new Response();
            int x = ((Double) request.getParameters().get(0)).intValue();
            int y = ((Double) request.getParameters().get(1)).intValue();
            response.setAnswer( "the xml form of object is:" +  xStream.toXML(Game.getGame().getSelectedCivilization().getVisibilityMap().map.get(x).get(y)));
            return response;
        }
        if (methodName.equals("getSelectedUnit")){
            Response response = new Response();
            response.setAnswer(xStream.toXML(gameMenuController.getSelectedUnit()));
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
                if (answer.getClass().equals(String.class)){
                    if (((String)answer).endsWith("successfully!")){
                        user = User.getUserByUsername((String) arguments[0]);
                    }
                }
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
