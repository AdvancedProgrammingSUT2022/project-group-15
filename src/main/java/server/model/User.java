package server.model;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import server.enums.Avatar;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User implements Comparable<User> {
    private static transient ArrayList<User> users = new ArrayList<>();

    private static transient final Gson gson = GlobalThings.gson;

    private Avatar avatar;
    private LocalDateTime lastScoreChangedTime;
    private LocalDateTime lastOnlineTime;
    private String username;
    private String password;
    private String nickname;
    private int score;

    private String authToken;

    public User(String username, String password, String nickname, int score) {
        setAvatar(Avatar.getRandomAvatar());
        this.lastScoreChangedTime = LocalDateTime.now();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = score;
    }

    /**
     * return the user with a specific username
     *
     * @author Parsa
     */
    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * return the user with a specific nickname
     *
     * @author Parsa
     */
    public static User getUserByNickname(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    /**
     * add a new user to the users list
     *
     * @author Parsa
     */
    public static void addUser(String username, String password, String nickname) {
        users.add(new User(username, password, nickname, 0));
        saveUsers();
    }

    /**
     * save users in UserDatabase.json
     * uses FxGson library
     *
     * @author Erfan & Parsa
     */
    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            fileWriter.write(gson.toJson(users));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load users created before(Saved in UserDatabase.json)
     * uses FxGson library
     *
     * @author Erfan & Parsa
     */
    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) {
                users = createdUsers;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccountOfLoggedInPlayer(User user) {
        users.remove(user);
        saveUsers();
    }


    /**
     * add amount to the user's score
     *
     * @param amount the amount that we want to add or subtract from user's score
     * @author Parsa
     */
    public void changeScore(int amount) {
        this.score = getScore() + amount;
        setLastScoreChangedTime(LocalDateTime.now());
        saveUsers();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }



    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public int compareTo(User o) {
        Integer myScore = this.getScore();
        Integer otherScore = o.getScore();
        if (!otherScore.equals(myScore))
            return otherScore.compareTo(myScore);
        if (!lastScoreChangedTime.equals(o.lastScoreChangedTime))
            return this.lastScoreChangedTime.compareTo(o.lastScoreChangedTime);
        return this.getUsername().compareTo(o.getUsername());
    }


    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public LocalDateTime getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(LocalDateTime lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
        saveUsers();
    }

    public String getOnlineTime() {
        if (this.lastOnlineTime != null)
            return this.lastOnlineTime.format(DateTimeFormatter.ofPattern("d MMM, uuuu HH:mm:ss"));
        else
            return "null";
    }

//    public static User getLoggedInUser() {
//        return loggedInUser;
//    }
//    public static void setLoggedInUser(User loggedInUser) {
//        User.loggedInUser = loggedInUser;
//    }


    public LocalDateTime getLastScoreChangedTime() {
        return lastScoreChangedTime;
    }

    public void setLastScoreChangedTime(LocalDateTime lastScoreChangedTime) {
        this.lastScoreChangedTime = lastScoreChangedTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
