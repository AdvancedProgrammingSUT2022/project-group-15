package model;

import controller.GameMenuController;
import enums.*;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Map {
    private int rowsNumber;
    private int columnsNumber;

    public ArrayList<ArrayList<Hex>> map;

    /*
   structure of map:
   “double-height” horizontal layout doubles row values

             y=0  y=1  y=2  y=3  y=4  y=5  y=6  y=7
   x=0      [0][0]   [0][2]   [0][4]     [0][6]
   x=1          [1][1]    [1][3]    [1][5]    [1][7]
   x=2      [2][0]   [2][2]   [2][4]     [2][6]
   x=3          [3][1]    [3][3]    [3][5]    [3][7]
   x=4      [4][0]   [4][2]   [4][4]     [4][6]
   x=5          [5][1]    [5][3]    [5][5]    [5][7]
   x=6      [6][0]   [6][2]   [6][4]     [6][6]

    structure of arrays
   “odd-q” vertical layout

            y=0  y=1  y=2  y=3  y=4  y=5  y=6  y=7
   x=0     [0][0]   [0][2]   [0][4]     [0][6]
               [0][1]    [0][3]    [0][5]    [0][7]
   x=1     [1][0]   [1][2]   [1][4]     [1][6]
               [1][1]    [1][3]    [1][5]    [1][7]
   */

    public boolean validCoordinateInArray(int x, int y) {
        return x >= 0 && y >= 0 && x < this.rowsNumber && y < this.columnsNumber;
    }


    public Map(int rowsNumber, int columnsNumber) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
    }


    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }


    public Map clone() {
        Map newMap = new Map(rowsNumber, columnsNumber);
        newMap.map = new ArrayList<>();
        for (int i = 0; i < rowsNumber; i++) {
            ArrayList<Hex> row = new ArrayList<>();
            for (int j = 0; j < columnsNumber; j++) {
                Hex hex = this.map.get(i).get(j).clone();
                row.add(hex);
            }
            newMap.map.add(row);
        }
        return newMap;
    }

    public void fillMap() {
        map = new ArrayList<>();
        for (int i = 0; i < rowsNumber; i++) {
            ArrayList<Hex> row = new ArrayList<>();
            for (int j = 0; j < columnsNumber; j++) {
                Hex hex = createRandomHex(i, j);
                row.add(hex);
            }
            map.add(row);
        }
        addingRiverOasisFlat();
        addingResources();
    }

    private void addingResources() {
        Random random = new Random();
        for (ArrayList<Hex> hexes : map) {
            for (Hex hex : hexes) {
                if (hex.getTerrain().equals(Terrain.MOUNTAIN) || hex.getTerrain().equals(Terrain.SNOWLAND) || hex.getTerrain().equals(Terrain.OCEAN))
                    continue;
                int percent = 10;
                //SCORING
                if (hex.getFeature().equals(Feature.DENSE_FOREST)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.BANANA);
                }
                if (hex.getTerrain().equals(Terrain.GRASSLAND)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.COW);
                }
                if (hex.getTerrain().equals(Terrain.TUNDRA) || hex.getTerrain().equals(Terrain.HILL) || hex.getFeature().equals(Feature.JUNGLE)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.GAZELLE);
                }
                if (hex.getTerrain().equals(Terrain.GRASSLAND) || hex.getTerrain().equals(Terrain.DESERT) ||
                        hex.getTerrain().equals(Terrain.HILL) || hex.getTerrain().equals(Terrain.PLAIN)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.SHEEP);
                }
                if (hex.getTerrain().equals(Terrain.PLAIN) || hex.getFeature().equals(Feature.FLAT)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.WHEAT);
                }
                //luxury
                if (hex.getTerrain().equals(Terrain.PLAIN) || hex.getTerrain().equals(Terrain.DESERT) || hex.getTerrain().equals(Terrain.GRASSLAND)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.COTTON);
                }
                if (hex.getFeature().equals(Feature.JUNGLE) || hex.getFeature().equals(Feature.DENSE_FOREST)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.COLOR);
                }
                if (hex.getFeature().equals(Feature.JUNGLE) || hex.getTerrain().equals(Terrain.TUNDRA)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.FUR);
                }
                if (true) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.GEM);
                }
                if (!hex.getTerrain().equals(Terrain.TUNDRA)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.GOLD);
                }
                if (hex.getTerrain().equals(Terrain.PLAIN) || hex.getTerrain().equals(Terrain.DESERT)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.FUMIGATION);
                }
                if (hex.getTerrain().equals(Terrain.PLAIN)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.TUSK);
                }
                if (true) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.MARBLE);
                }
                if (hex.getFeature().equals(Feature.JUNGLE)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.SILK);
                }
                if (hex.getTerrain().equals(Terrain.TUNDRA) || hex.getTerrain().equals(Terrain.HILL) || hex.getTerrain().equals(Terrain.DESERT)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.SILVER);
                }
                if (hex.getFeature().equals(Feature.SWAMP) || hex.getFeature().equals(Feature.FLAT)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.SUGAR);
                }
                //strategic
                if (hex.getTerrain().equals(Terrain.GRASSLAND) || hex.getTerrain().equals(Terrain.HILL) || hex.getTerrain().equals(Terrain.PLAIN)) {
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.COAL);
                }
                if (hex.getTerrain().equals(Terrain.TUNDRA)||hex.getTerrain().equals(Terrain.PLAIN)|| hex.getTerrain().equals(Terrain.GRASSLAND)){
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.HORSE);
                }
                if (true){
                    if (random.nextInt(100) < percent)
                        hex.setResource(Resource.IRON);
                }

            }
        }
    }

    private void addingRiverOasisFlat() {
        Random random = new Random();
        for (ArrayList<Hex> hexes : map) {
            for (Hex hex : hexes) {
                if (random.nextInt() < 20 &
                        !inAroundOcean(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'))) {
                    hex.setHasRiver(true);
                }
                if (hex.getTerrain().equals(Terrain.DESERT)) {
                    if (random.nextInt(100) < 15) {
                        hex.setFeature(Feature.OASIS);
                    }
                }
            }
        }

        for (ArrayList<Hex> hexes : map) {
            for (Hex hex : hexes) {
                if (hex.doesHaveRiver()) {
                    if (!riverAround(hex.getCoordinatesInArray().get('x'), hex.getCoordinatesInArray().get('y'))) {
                        hex.setHasRiver(false);
                    } else {
                        if (random.nextInt(100) < 20) {
                            hex.setFeature(Feature.FLAT);
                        }
                    }
                }
            }
        }
    }

    private boolean riverAround(int x, int y) {
        for (NeighborHex neighborHex : NeighborHex.values()) {
            y = 2 * y + x % 2 + neighborHex.yDiff;
            y /= 2;
            x = x + neighborHex.xDiff;


            if (this.validCoordinateInArray(x, y)) {
                if (map.get(x).get(y).doesHaveRiver())
                    return true;
            }
        }

        return false;
    }

    private Hex createRandomHex(int x, int y) {
        Random random = new Random();

        int randomNumber = random.nextInt(100);
        if (inPoles(x)) {

            if (randomNumber < 80)
                return new Hex(Terrain.SNOWLAND, Feature.ICE, Resource.NULL, false, x, y);
            else
                return new Hex(Terrain.SNOWLAND, Feature.NULL, Resource.NULL, false, x, y);
        }
        if (inAroundOcean(x, y)) {
            if (randomNumber < 90)
                return new Hex(Terrain.OCEAN, Feature.NULL, Resource.NULL, false, x, y);
            else if (randomNumber < 95)
                return new Hex(Terrain.MOUNTAIN, Feature.NULL, Resource.NULL, false, x, y);
            else
                return new Hex(Terrain.PLAIN, Feature.DENSE_FOREST, Resource.NULL, false, x, y);
        }
        if (percentInMiddleX(x) > 80) { // tropical
            if (randomNumber < 33)
                return new Hex(Terrain.PLAIN, randomFeatureTropical(random), Resource.NULL, false, x, y);
            else if (randomNumber < 66)
                return new Hex(Terrain.HILL, randomFeatureTropical(random), Resource.NULL, false, x, y);
            else
                return new Hex(Terrain.GRASSLAND, randomFeatureTropical(random), Resource.NULL, false, x, y);
        }
        if (percentInMiddleX(x) < 20) { // near poles
            if (randomNumber < 33)
                return new Hex(Terrain.PLAIN, randomFeatureNearPole(random), Resource.NULL, false, x, y);
            else if (randomNumber < 60)
                return new Hex(Terrain.TUNDRA, randomFeatureNearPole(random), Resource.NULL, false, x, y);
            else if (randomNumber < 70)
                return new Hex(Terrain.MOUNTAIN, randomFeatureNearPole(random), Resource.NULL, false, x, y);
            else if (randomNumber < 80)
                return new Hex(Terrain.SNOWLAND, randomFeatureNearPole(random), Resource.NULL, false, x, y);
            else
                return new Hex(Terrain.HILL, randomFeatureNearPole(random), Resource.NULL, false, x, y);
        }
        //between pole and tropical
        if (randomNumber < 30)
            return new Hex(Terrain.DESERT, randomFeatureInBetween(random), Resource.NULL, false, x, y);
        else if (randomNumber < 60)
            return new Hex(Terrain.GRASSLAND, randomFeatureInBetween(random), Resource.NULL, false, x, y);
        else if (randomNumber < 67)
            return new Hex(Terrain.MOUNTAIN, randomFeatureInBetween(random), Resource.NULL, false, x, y);
        else if (randomNumber < 80)
            return new Hex(Terrain.HILL, randomFeatureInBetween(random), Resource.NULL, false, x, y);
        else
            return new Hex(Terrain.PLAIN, randomFeatureInBetween(random), Resource.NULL, false, x, y);
    }

    private Feature randomFeatureTropical(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 25)
            return Feature.NULL;
        else if (randomNumber < 40)
            return Feature.SWAMP;
        else if (randomNumber < 55)
            return Feature.DENSE_FOREST;
        else
            return Feature.JUNGLE;
    }

    private Feature randomFeatureNearPole(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 35)
            return Feature.NULL;
        else if (randomNumber < 50)
            return Feature.SWAMP;
        else if (randomNumber < 68)
            return Feature.DENSE_FOREST;
        else if (randomNumber < 95)
            return Feature.JUNGLE;
        else
            return Feature.ICE;
    }

    private Feature randomFeatureInBetween(Random random) {
        int randomNumber = random.nextInt(100);
        if (randomNumber < 70)//oasis flat
            return Feature.NULL;
        else if (randomNumber < 85)
            return Feature.JUNGLE;
        else if (randomNumber < 90)
            return Feature.DENSE_FOREST;
        else
            return Feature.SWAMP;
    }


    private boolean inPoles(int x) {
        return x < 2 || x >= rowsNumber - 2;
    }

    private boolean inAroundOcean(int x, int y) {
        return percentInMiddleX(x) * 1.5 + percentInMiddleY(y) < 80;
    }

    private int percentInMiddleX(int x) {
        int differenceFromMiddle = abs(rowsNumber / 2 - x);
        return 100 - differenceFromMiddle * 100 / (rowsNumber / 2);
    }

    private int percentInMiddleY(int y) {
        int differenceFromMiddle = abs(columnsNumber / 2 - y);
        return 100 - differenceFromMiddle * 100 / (columnsNumber / 2);
    }

    public boolean hasTheCityAround(int x,int y , City city){
        for (NeighborHex neighborHex : NeighborHex.values()) {
            if (map.get((2*x+y%2+neighborHex.xDiff)/2).get(y+ neighborHex.yDiff).getCity().equals(city))
                return true;
        }
        return false;

    }

}
