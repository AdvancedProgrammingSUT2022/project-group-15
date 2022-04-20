package controller;

import model.Civilization;
import model.Game;
import model.User;

import java.util.ArrayList;

public class MainMenuController{
    /**
     * create and start game
     * @param users users for the game
     * @author amir
     */
    public void startGame (ArrayList<User> users){
        Game.getGame().startNewGame(users);
    }
}
