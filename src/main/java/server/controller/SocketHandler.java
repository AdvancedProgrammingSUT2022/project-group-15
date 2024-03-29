package server.controller;


import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import server.model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;


public class SocketHandler extends Thread {
    private CommandSender commandSender;
    private boolean isYourTurn = true;
    private boolean isPlayingGame = false;

    private User user = null;
    private ArrayList<SocketHandler> waitingInLobbyWithYou = new ArrayList<>();
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    private final XStream xStream = new XStream();


    private String menu = "Login";

    private GameMenuController gameMenuController;
    private GameSettingMenuController gameSettingMenuController;
    private LoginMenuController loginMenuController = new LoginMenuController();
    private MainMenuController mainMenuController;
    private ProfileMenuController profileMenuController;
    private ScoreBoardController scoreBoardController;
    private FriendshipController friendShipController;

    public SocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        waitingInLobbyWithYou.add(this);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
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
                Gson gson = GlobalThings.gson;
                String s = dataInputStream.readUTF();
                //System.out.println("<<REQUEST>> : \n" + s); // TODO : delete this line
                Request request = gson.fromJson(s, Request.class);
                //System.out.println("New request from " + socket);
                Response response = handleRequest(request);
               // System.out.println("<<RESPONSE>> : \n" + gson.toJson(response)); // TODO : delete this line
                dataOutputStream.writeUTF(gson.toJson(response));
                dataOutputStream.flush();
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            if (user != null)
                user.setLastOnlineTime(LocalDateTime.now());
            exception.printStackTrace();
            ServerController.getInstance().removeSocket(this);
            // TODO : send updated list of users to online users
        }
    }

    private Response handleRequest(Request request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = request.getMethodName();
        if (methodName.equals("getPlayersInLobby")) {
            Response response = new Response();
            ArrayList<String> usernames = new ArrayList<>();
            for (SocketHandler socketHandler : waitingInLobbyWithYou) {
                usernames.add(socketHandler.user.getUsername());
            }
            response.setAnswer(usernames);
            return response;
        }
        if (methodName.startsWith("accept invite from")) {
            String username = methodName.substring(19);
            for (SocketHandler socketHandler : ServerController.getInstance().getSocketHandlers()) {
                if (socketHandler.user.getUsername().equals(username)) {
                    socketHandler.waitingInLobbyWithYou.add(this);
                    this.waitingInLobbyWithYou = socketHandler.waitingInLobbyWithYou;
                    socketHandler.sendCommand(user.getUsername() + " has accepted your invite");
                    for (SocketHandler handler : waitingInLobbyWithYou) {
                        handler.sendCommand("update players");
                    }
                }
            }
            return new Response();
        }
        if (methodName.startsWith("reject invite from")) {
            String username = methodName.substring(19);
            for (SocketHandler socketHandler : ServerController.getInstance().getSocketHandlers()) {
                if (socketHandler.user.getUsername().equals(username)) {
                    socketHandler.sendCommand(user.getUsername() + " has rejected your invite");
                }
            }
            return new Response();
        }
        if (methodName.equals("getUserByIndex")) {
            int index = ((Double) request.getParameters().get(0)).intValue();
            Response res = new Response();
            Collections.sort(User.getUsers());
            res.setAnswer(User.getUsers().get(index).toJson());
            return res;
        }
        if (methodName.startsWith("change menu")) {
            changeMenu(methodName.substring(12));
            return new Response();
        }
        if (methodName.equals("getUser")) {
            Response response = new Response();
            response.setAnswer(User.getUserByUsername((String) request.getParameters().get(0)).toJson());
            return response;
        }
        if (methodName.equals("getMyUser")) {
            Response response = new Response();
            response.setAnswer(user.toJson());
            return response;
        }
        if (methodName.equals("getGame")) {
            Response response = new Response();
            response.setAnswer(xStream.toXML(Game.getGame()));
            return response;
        }
        if (methodName.equals("getHex")) {
            Response response = new Response();
            int x = ((Double) request.getParameters().get(0)).intValue();
            int y = ((Double) request.getParameters().get(1)).intValue();
            response.setAnswer("the xml form of object is:" + xStream.toXML(Game.getGame().getSelectedCivilization().getVisibilityMap().map.get(x).get(y)));
            return response;
        }
        if (methodName.equals("getSelectedUnit")) {
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
                if (answer instanceof String) {
                    if (((String) answer).startsWith("change turn done \nIt's now your turn "))
                        updateMapForClients();
                }
                break;
            case "GameSetting":
                method = gameSettingMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(gameSettingMenuController, arguments);
                if (answer instanceof String) {
                    if (((String) answer).startsWith("a new game started with ")) {
                        new Thread(()->{
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            for (SocketHandler socketHandler : waitingInLobbyWithYou) {
                                socketHandler.sendCommand("game started");
                            }
                            updateMapForClients();
                        }).start();

                    }
                }
                break;
            case "Login":
                method = loginMenuController.getClass().getMethod(methodName, types);
                answer = method.invoke(loginMenuController, arguments);
                if (answer.getClass().equals(String.class)) {
                    if (((String) answer).endsWith("successfully!")) {
                        user = User.getUserByUsername((String) arguments[0]);
                        user.setLastOnlineTime(null);
                        int random = (int) (Math.random() * 100);
                        user.setAuthToken(user.getUsername() + user.getPassword() + user.getNickname() + String.valueOf(random));
                        answer += user.getAuthToken();
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
            case "Score":
                method = scoreBoardController.getClass().getMethod(methodName, types);
                answer = method.invoke(scoreBoardController, arguments);
                break;
            case "Friend":
                method = friendShipController.getClass().getMethod(methodName, types);
                answer = method.invoke(friendShipController, arguments);
                break;

        }
        Response response = new Response();
        response.setAnswer(answer);
        return response;
    }

    private void updateMapForClients() {
        for (SocketHandler socketHandler : waitingInLobbyWithYou) {
            if (socketHandler.user.getUsername().equals(Game.getGame().getSelectedCivilization().getUsername())) {
                socketHandler.sendCommand("its your turn");
            }
            else {
                socketHandler.sendCommand("not your turn. turn for : " + Game.getGame().getSelectedCivilization().getUsername());
            }
        }
    }

    private void changeMenu(String name) {
        menu = name;
        System.out.println("you are now in " + name);
        switch (menu) {
            case "Game":
                gameMenuController = new GameMenuController();
                isPlayingGame = true;
                isYourTurn = Game.getGame().getSelectedCivilization().getUsername().equals(user.getUsername());
                break;
            case "GameSetting":
                gameSettingMenuController = new GameSettingMenuController(user);
                //todo
                break;
            case "Login":
                loginMenuController = new LoginMenuController();
                break;
            case "Main":
                mainMenuController = new MainMenuController();
                break;
            case "Profile":
                profileMenuController = new ProfileMenuController(user);
                break;
            case "Score":
                scoreBoardController = new ScoreBoardController();
                break;
            case "Friend":
                friendShipController = new FriendshipController();
                break;
        }
    }

    public void sendCommand(String command) {
        Response response = new Response();
        response.setAnswer(command);
        commandSender.sendCommand(response);
    }

}
