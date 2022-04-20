package model;

import enums.Technology;

import java.util.ArrayList;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    //visibility map???
    private ArrayList<Technology> technologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<Unit> openedUnits;

    public Civilization(User user) {
        this.user = user;
    }

}
