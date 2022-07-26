package client.model;

import com.google.gson.*;
import client.enums.Avatar;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User implements Comparable<User> {

    private transient static final Gson gson = GlobalThings.gson;

    private Avatar avatar;
    private LocalDateTime lastScoreChangedTime;
    private LocalDateTime lastOnlineTime;
    private String username;
    private String password;
    private String nickname;
    private int score;
    ArrayList<String> friendReqs = new ArrayList<>();
    ArrayList<String> friends = new ArrayList<>();

    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }

    public String toJson() {
        return gson.toJson(this);
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

    public LocalDateTime getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(LocalDateTime lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getOnlineTime() {
        if (this.lastOnlineTime != null)
            return this.lastOnlineTime.format(DateTimeFormatter.ofPattern("d MMM, uuuu HH:mm:ss"));
        else
            return "online";
    }



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
}
