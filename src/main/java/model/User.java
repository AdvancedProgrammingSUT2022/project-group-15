package model;

import java.util.ArrayList;

public class User {
    private static final ArrayList<User> users = new ArrayList<>();
    public static User loggedInUser = null;

    private String username;
    private String password;
    private String nickname;
    private int score;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
    }

    /**
     * return the user with a specific username
     * @author Parsa
     */
    public static User getUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * return the user with a specific nickname
     * @author Parsa
     */
    public static User getUserByNickname(String nickname) {
        for (User user : users) {
            if(user.getNickname().equals(nickname)){
                return user;
            }
        }
        return null;
    }

    /**
     * add a new user to the users list
     * @author Parsa
     */
    public static void addUser(String username, String password, String nickname){
        users.add(new User(username, password, nickname));
    }

    /**
     * add amount to the user's score
     * @param amount the amount that we want to add or subtract from user's score
     * @author Parsa
     */
    public void changeScore(int amount){
        this.score += amount;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
