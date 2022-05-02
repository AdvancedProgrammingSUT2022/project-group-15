package model;

import model.unit.RangedMilitary;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class City {
    private String name;
    private Civilization owner;
    private int neededProduction;
    private Unit unitInProgress;
    private int numberOfCitizen;
    private HashMap<Character, Integer> coordinatesOfCenter = new HashMap<>();
    private int foodStorage;
    private int foodPerTurn;
    private int productionPerTurn;
    private int goldPerTurn;
    private int sciencePerTurn;
    private final ArrayList<Hex> cityHexes = new ArrayList<>();

    private RangedMilitary cityUnit;
    //rangedmilitary not added

    public City(String name, int x, int y) {
        this.name = name;
        // complete coordinate
    }

    public ArrayList<Hex> getCityHexes() {
        return cityHexes;
    }
}
