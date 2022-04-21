package model;

import enums.UnitName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Unit {
    private HashMap<Character, Integer> coordinates = new HashMap<>();
    private Civilization owner;
    private int movementSpeed;
    private int remainingMovement;
    private int experience;
    private int cost;
    private UnitName name;
    private int health;
    private boolean isSleep;

    public Unit(int x, int y, Civilization owner, int movementSpeed, int health, UnitName name) {
        coordinates.put('x', x);
        coordinates.put('y', y);
        this.owner = owner;
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.name = name;
    }

    public void move(int x, int y) {
        //Game.getGame().map.get(its x).get(its y). change position
        this.coordinates.replace('x', x);
        this.coordinates.replace('y', y);
        //Game.getGame().map.get(x).get(y). change position
    }


    private ArrayList<Hex> moveCostByDijkstra(int x, int y) {
        return null;
    }


    //override in children
    public boolean needsCommand() {
        return true;
    }


    public void cancelMission() {

    }

    public void deleteUnit(boolean isSelling) {

    }


}
