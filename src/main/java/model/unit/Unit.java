package model.unit;

import controller.GameMenuController;
import enums.NeighborHex;
import enums.UnitName;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit {
    protected HashMap<Character, Integer> coordinatesInMap = new HashMap<>();
    protected ArrayList<Hex> PlanedToGo = new ArrayList<>();
    protected Civilization owner;
    protected int movementSpeed;
    protected int remainingMovement;
    protected int experience;
    protected int cost;
    protected UnitName name;
    protected int nowHealth;
    protected int totalHealth;
    protected boolean isSleep;
    protected int meleePower;//add to constructor

    public Unit(int x, int y, Civilization owner, int movementSpeed, int totalHealth, UnitName name) {
        coordinatesInMap.put('x', x);
        coordinatesInMap.put('y', y);
        this.owner = owner;
        this.movementSpeed = movementSpeed;
        this.totalHealth = totalHealth;
        this.name = name;
        if (this instanceof CivilUnit)
            Game.getGame().map.map.get(x).get(y).setCivilUnit((CivilUnit) this);
        else
            Game.getGame().map.map.get(x).get(y).setMilitaryUnit((MilitaryUnit) this);
    }

    public Civilization getOwner() {
        return owner;
    }

    public HashMap<Character, Integer> getCoordinatesInMap() {
        return coordinatesInMap;
    }

    public UnitName getName() {
        return name;
    }

    public ArrayList<Hex> getPlanedToGo() {
        return PlanedToGo;
    }

    public void doPlanedMovement() {
        Hex nextHex;
        while (remainingMovement > 0 || !PlanedToGo.isEmpty()) {
            nextHex = PlanedToGo.get(0);
            moveToHex(nextHex.getCoordinatesInArray().get('x'), nextHex.getCoordinatesInArray().get('y'));
            PlanedToGo.remove(0);
        }
        if (PlanedToGo.isEmpty())
            PlanedToGo = null;
    }

    protected void moveToHex(int x, int y) {
        if (this instanceof MilitaryUnit) {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x')).get(this.coordinatesInMap.get('y') / 2).setMilitaryUnit(null);
        } else {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x')).get(this.coordinatesInMap.get('y') / 2).setCivilUnit(null);
        }

        this.coordinatesInMap.replace('x', x);
        this.coordinatesInMap.replace('y', 2 * y + x % 2);
        this.remainingMovement -= Game.getGame().map.map.get(this.coordinatesInMap.get('x')).get(this.coordinatesInMap.get('y') / 2).getMovementPrice();

        if (this instanceof MilitaryUnit) {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x')).get(this.coordinatesInMap.get('y') / 2).setMilitaryUnit((MilitaryUnit) this);
        } else {
            Game.getGame().map.map.get(this.coordinatesInMap.get('x')).get(this.coordinatesInMap.get('y') / 2).setCivilUnit((CivilUnit) this);
        }
        this.owner.adjustVisibility();
    }


    public int findShortestPathByDijkstra(int x, int y) {
        int numberOfRows = Game.getGame().getRows();
        int numberColumns = Game.getGame().getColumns();
        int numberOfNodes = numberColumns * numberOfRows;
        int[] parent = new int[numberOfNodes];
        int startNodeNumber = this.coordinatesInMap.get('x') * numberColumns + this.coordinatesInMap.get('y') / 2;
        int destinationNode = x * numberColumns + y;
        // Key values used to pick minimum weight edge in cut
        int[] distance = new int[numberOfNodes];
        // To represent set of vertices included in MST
        Boolean[] mstSet = new Boolean[numberOfNodes];
        // Initialize all keys as INFINITE
        for (int i = 0; i < numberOfNodes; i++) {
            distance[i] = Integer.MAX_VALUE / 2;
            mstSet[i] = false;
        }
        // Always include first 1st vertex in MST.
        distance[startNodeNumber] = 0; // Make distance 0 so that this vertex is
        // picked as first vertex
        parent[startNodeNumber] = -1; // First node is always root of MST
        // The MST will have numberOfNodes vertices
        for (int count = 0; count < numberOfNodes - 1; count++) {
            // Pick thd minimum distance vertex from the set of vertices
            // not yet included in MST
            int u = minKey(distance, mstSet, numberOfNodes);
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
            // Update distance value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            // graph[u][v] is non zero only for adjacent vertices of m
            // mstSet[v] is false for vertices not yet included in MST
            // Update the distance only if graph[u][v] is smaller than distance[v]
            for (NeighborHex neighborHex : NeighborHex.values()) {
                updateAdjacentNode(neighborHex.xDiff, neighborHex.yDiff, distance, mstSet, u, parent);
            }

        }
        createArraylistForRoute(parent, destinationNode);
        if (distance[destinationNode] > 999999)
            PlanedToGo = null;
        return distance[destinationNode];
    }

    private void createArraylistForRoute(int[] parent, int destinationNode) {
        ArrayList<Hex> answer = new ArrayList<>();

        int x = destinationNode / (Game.getGame().getColumns());
        int y = destinationNode % (Game.getGame().getColumns());
        answer.add(0, Game.getGame().map.map.get(x).get(y));

        destinationNode = parent[destinationNode];
        while (parent[destinationNode] != -1) {
            x = destinationNode / (Game.getGame().getColumns());
            y = destinationNode % (Game.getGame().getColumns());
            answer.add(0, Game.getGame().map.map.get(x).get(y));
            destinationNode = parent[destinationNode];
        }

        PlanedToGo = answer;
    }

    private void updateAdjacentNode(int xDiff, int yDiff, int[] distance, Boolean[] mstSet, int NodeNumber, int[] parent) {
        int x = NodeNumber / (Game.getGame().getColumns());
        int y = NodeNumber % (Game.getGame().getColumns());
        y = 2 * y + x % 2 + yDiff;
        y = y / 2;
        x += xDiff;

        int destinationNodeNumber = x * (Game.getGame().getColumns()) + y;
        if (!Game.getGame().map.validCoordinateInArray(x, y))
            return;
        int moveCost = Game.getGame().map.map.get(x).get(y).getMovementPrice();
        if (Game.getGame().map.map.get(x).get(y).doesHaveRiver() && Game.getGame().map.map.
                get(NodeNumber / (Game.getGame().getColumns())).get(NodeNumber % (Game.getGame().getColumns())).doesHaveRiver())
            moveCost = this.movementSpeed;
        if (moveCost == -1)
            moveCost = Integer.MAX_VALUE / 2;

        if (mstSet[destinationNodeNumber] == false && distance[NodeNumber] + moveCost < distance[destinationNodeNumber] &&
                !hasSameUnitInHex(x, y)) {
            parent[destinationNodeNumber] = NodeNumber;
            distance[destinationNodeNumber] = distance[NodeNumber] + moveCost;
        }
    }

    private boolean hasSameUnitInHex(int x, int y) {
        if (this instanceof CivilUnit) {
            if (Game.getGame().map.map.get(x).get(y).getCivilUnit() == null)
                return false;
        } else if (Game.getGame().map.map.get(x).get(y).getMilitaryUnit() == null)
            return false;
        return true;
    }


    private int minKey(int[] key, Boolean[] mstSet, int numberOfNodes) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numberOfNodes; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }


    abstract public boolean needsCommand();

    abstract public void cancelMission();

    public void deleteUnit(boolean isSelling) {
        if (isSelling)
            this.owner.setGoldStorage(this.owner.getGoldStorage() + this.cost / 5);//is 5 ok?
        this.owner.deleteUnit(this);
    }

}
