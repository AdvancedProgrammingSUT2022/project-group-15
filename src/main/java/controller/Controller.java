package controller;

import model.User;
import view.*;

public class Controller {
    protected LoginMenu loginMenu = new LoginMenu();
    protected MainMenu mainMenu = new MainMenu();
    protected ProfileMenu profileMenu = new ProfileMenu();
    protected GameMenu gameMenu = new GameMenu();

    public void run() {
        if (loginMenu.run().equals("exit")) return;

        while (true) {
            switch (mainMenu.run()){
                case "game menu":
                    gameMenu.run();
                    break;
                case "profile menu":
                    profileMenu.run();
                    break;
                case "logout":
                    if (loginMenu.run().equals("exit")) return;
                    break;
            }
        }
    }
}
