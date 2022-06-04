package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Avatar;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import org.hildan.fxgson.FxGson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User implements Comparable<User>{
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    private Avatar avatar;
    private final ObjectProperty<Image> avatarImage = new SimpleObjectProperty<>();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty nickname = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();

    public User(String username, String password, String nickname, int score) {
        setAvatar(Avatar.getRandomAvatar());
        this.username.setValue(username);
        this.password.setValue(password);
        this.nickname.setValue(nickname);
        this.score.setValue(score);
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
     *
     * @author Erfan & Parsa
     */
    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            fileWriter.write(gson.toJson(users));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load users created before(Saved in UserDatabase.json)
     *
     * @author Erfan & Parsa
     */
    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) users = createdUsers;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getNickname() {
        return nickname.get();
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }


    /**
     * add amount to the user's score
     *
     * @param amount the amount that we want to add or subtract from user's score
     * @author Parsa
     */
    public void changeScore(int amount) {
        this.score.setValue(this.score.get() + amount);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser(){
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public ObjectProperty<Image> avatarProperty() {
        return avatarImage;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        this.avatarImage.set(avatar.image);
    }

    @Override
    public int compareTo(User o) {
        // TODO : implement for scoreboard
        Integer myScore = this.getScore();
        Integer otherScore = o.getScore();
        return otherScore.compareTo(myScore);
    }
}
