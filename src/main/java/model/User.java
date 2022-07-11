package model;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import enums.Avatar;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import org.hildan.fxgson.FxGson;

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
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;
    private static final Gson gson = createMyGson();

    @Expose(serialize = false)
    private ObjectProperty<Image> avatarImage = new SimpleObjectProperty<>();

    @Expose
    private Avatar avatar;
    @Expose
    private LocalDateTime lastScoreChangedTime;
    @Expose
    private LocalDateTime lastOnlineTime;
    @Expose
    private final StringProperty username = new SimpleStringProperty();
    @Expose
    private final StringProperty password = new SimpleStringProperty();
    @Expose
    private final StringProperty nickname = new SimpleStringProperty();
    @Expose
    private final IntegerProperty score = new SimpleIntegerProperty();

    public User(String username, String password, String nickname, int score) {
        setAvatar(Avatar.getRandomAvatar());
        this.lastScoreChangedTime = LocalDateTime.now();
        this.score.addListener(e -> this.lastScoreChangedTime = LocalDateTime.now());
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
     * creates a Gson that has been customized
     * @return the customized Gson object
     * @author Parsa
     */
    private static Gson createMyGson() {
        return FxGson.coreBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(),
                        DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(formatter.format(localDateTime));
            }
        }).setPrettyPrinting().disableHtmlEscaping().create();
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
                for (User user : createdUsers) {
                    user.avatarImage = new SimpleObjectProperty<>();
                    user.setAvatar(user.getAvatar());
                }
                users = createdUsers;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccountOfLoggedInPlayer(){
        users.remove(User.getLoggedInUser());
        saveUsers();
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
        saveUsers();
    }

    public String getNickname() {
        return nickname.get();
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
        saveUsers();
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
        saveUsers();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
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
        saveUsers();
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

    public LocalDateTime getLastScoreChanged() {
        return lastScoreChangedTime;
    }

    public void setLastScoreChanged(LocalDateTime lastScoreChanged) {
        this.lastScoreChangedTime = lastScoreChanged;
    }
}
