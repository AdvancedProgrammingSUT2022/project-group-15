package model;

import enums.UnitName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public abstract class Unit {
    private HashMap<Character, Integer> coordinates = new HashMap<>();
    private ArrayList<Hex> PlanedToGo = new ArrayList<>();
    private Civilization owner;
    private int movementSpeed;
    private int remainingMovement;
    private int experience;
    private int cost;
    private UnitName name;
    private int nowHealth;
    private int totalHealth;
    private boolean isSleep;

    public Unit(int x, int y, Civilization owner, int movementSpeed, int totalHealth, UnitName name) {
        coordinates.put('x', x);
        coordinates.put('y', y);
        this.owner = owner;
        this.movementSpeed = movementSpeed;
        this.totalHealth = totalHealth;
        this.name = name;
    }

    public void move(int x, int y) {
        findShortestPathByDijkstra(x, y);
        Hex nextHex = PlanedToGo.get(0);
        moveToHex(nextHex.getCoordinates().get('x'), nextHex.getCoordinates().get('y'));
        PlanedToGo.remove(0);
    }

    private void moveToHex(int x, int y) {
        if (this instanceof MilitaryUnit) {
            Game.getGame().map.get(this.coordinates.get('x')).get(this.coordinates.get('y')).setMilitaryUnit(null);
        } else {
            Game.getGame().map.get(this.coordinates.get('x')).get(this.coordinates.get('y')).setCivilUnit(null);
        }

        this.coordinates.replace('x', x);
        this.coordinates.replace('y', y);
        this.movementSpeed -= Game.getGame().map.get(this.coordinates.get('x')).get(this.coordinates.get('y')).getMovementPrice();

        if (this instanceof MilitaryUnit) {
            Game.getGame().map.get(this.coordinates.get('x')).get(this.coordinates.get('y')).setMilitaryUnit((MilitaryUnit) this);
        } else {
            Game.getGame().map.get(this.coordinates.get('x')).get(this.coordinates.get('y')).setCivilUnit((CivilUnit) this);
        }
    }


    private void findShortestPathByDijkstra(int x, int y) {
        int numberOfRows = Game.getGame().getRows() + 1;
        int numberColumns = Game.getGame().getColumns() + 1;
        int numberOfNodes = numberColumns * numberOfRows;
        int[] parent = new int[numberOfNodes];
        int startNodeNumber = this.coordinates.get('x') * numberColumns + this.coordinates.get('y');
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
            updateAdjacentNode(0, 1, distance, mstSet, u, parent);
            updateAdjacentNode(0, -1, distance, mstSet, u, parent);
            updateAdjacentNode(1, 0, distance, mstSet, u, parent);
            updateAdjacentNode(-1, 0, distance, mstSet, u, parent);
            updateAdjacentNode(1, 1, distance, mstSet, u, parent);
            updateAdjacentNode(-1, 1, distance, mstSet, u, parent);

        }
        createArraylistForRoute(parent, destinationNode);
    }

    private void createArraylistForRoute(int[] parent, int destinationNode) {
        ArrayList<Hex> answer = new ArrayList<>();

        int x = destinationNode / (Game.getGame().getColumns() + 1);
        int y = destinationNode % (Game.getGame().getColumns() + 1);
        answer.add(0, Game.getGame().map.get(x).get(y));

        destinationNode = parent[destinationNode];
        while (parent[destinationNode] != -1) {
            x = destinationNode / (Game.getGame().getColumns() + 1);
            y = destinationNode % (Game.getGame().getColumns() + 1);
            answer.add(0, Game.getGame().map.get(x).get(y));
        }

        PlanedToGo = answer;
    }

    private void updateAdjacentNode(int xDiff, int yDiff, int[] distance, Boolean[] mstSet, int NodeNumber, int[] parent) {
        int x = NodeNumber / (Game.getGame().getColumns() + 1) + xDiff;
        int y = NodeNumber % (Game.getGame().getColumns() + 1) + yDiff;
        int destinationNodeNumber = x * (Game.getGame().getColumns() + 1) + y;
        if (!validCoordinate(x, y))
            return;

        int moveCost = Game.getGame().map.get(x).get(y).getMovementPrice();
        if (Game.getGame().map.get(x).get(y).doesHaveRiver() && Game.getGame().map.get(x - xDiff).get(y - yDiff).doesHaveRiver())
            moveCost = this.movementSpeed;
        if (moveCost == -1)
            moveCost = Integer.MAX_VALUE / 2;

        if (mstSet[destinationNodeNumber] == false && distance[NodeNumber] + moveCost < distance[destinationNodeNumber]) {
            parent[destinationNodeNumber] = NodeNumber;
            distance[destinationNodeNumber] = distance[NodeNumber] + moveCost;
        }
    }

    private boolean validCoordinate(int x, int y) {
        if (x < 0 || y < 0 || x > Game.getGame().getRows() || y > Game.getGame().getColumns())
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


    //override in children
    abstract public boolean needsCommand();


    public void cancelMission() {

    }

    public void deleteUnit(boolean isSelling) {

    }


}
