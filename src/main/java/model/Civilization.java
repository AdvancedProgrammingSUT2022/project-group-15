package model;

import enums.HexVisibility;
import enums.Resource;
import enums.Technology;
import enums.UnitName;

import java.util.ArrayList;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    private ArrayList<ArrayList<HexVisibility>>  visibilityMap ;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<UnitName> openedUnits = new ArrayList<>();
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

    public void deleteUnit(Unit unit){
        units.remove(unit);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }
    // TODO: 4/20/2022  getmap()

    public void adjustVisibility(){

    }


}
