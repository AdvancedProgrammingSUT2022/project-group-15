package model;

import enums.HexVisibility;
import enums.Resource;
import enums.Technology;

import java.util.ArrayList;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    private ArrayList<ArrayList<HexVisibility>>  visibilityMap ;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<Unit> openedUnits = new ArrayList<>();
    private ArrayList<Resource> openedResources = new ArrayList<>();
    private City capital;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private int goldStorage = 0;
    private int scienceStorage = 0;
    private int sciencePerTurn = 0;
    private int happiness = 0;


    public Civilization(User user) {
        this.user = user;
    }
    // TODO: 4/20/2022  getmap() adjustvisibility();

}
