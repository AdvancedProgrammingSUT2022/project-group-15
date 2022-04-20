package model;

import enums.UnitName;

import java.util.HashMap;

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
        //x , y
        this.owner = owner;
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.name = name;
    }


}
