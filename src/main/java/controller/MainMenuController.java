package controller;

import model.Civilization;
import model.Game;
import model.User;

import java.util.ArrayList;

public class MainMenuController extends Controller{
    /**
     * create and start game
     * @param users users for the game
     * @author amir
     */
    public void startGame (ArrayList<User> users){
        for (User user : users) {
            Civilization tmp = new Civilization(user);
            Game.getGame().getCivilizations().add(tmp);
        }
    }
}
