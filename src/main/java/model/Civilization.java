package model;

import enums.*;
import model.unit.SettlerUnit;
import model.unit.Unit;
import model.unit.WorkerUnit;

import java.util.ArrayList;
import java.util.Random;

public class Civilization {
    private User user;
    private boolean isYourTurn;
    private Map visibilityMap;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private ArrayList<Technology> availableTechnologies = new ArrayList<>();
    private Technology technologyInProgress;
    private ArrayList<UnitName> openedUnits = new ArrayList<>();
    private ArrayList<Resource> openedResources = new ArrayList<>();
    private ArrayList<Feature> openedFeatures = new ArrayList<>();
    private ArrayList<Improvement> openedImprovements = new ArrayList<>();
    private City capital;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private int goldStorage = 0;
    private int scienceStorage = 0;
    private int sciencePerTurn = 0;
    private int happiness = 0;


    public Civilization(User user) {
        this.user = user;
        visibilityMap = Game.getGame().map.clone();
    }

    public void nextTurn() {
        scienceStorage += sciencePerTurn;
        if (technologyInProgress != null && scienceStorage >= technologyInProgress.cost) {
            openNewTechnology();
            updateAvailableTechnologies();
        }
        sciencePerTurn = calculateSciencePerTurn();
        goldStorage += calculateGoldPerTurn();
    }

    public void updateAvailableTechnologies() {
        availableTechnologies.clear();
        for (Technology tech : Technology.values()) {
            if (Game.getGame().getSelectedCivilization().getTechnologies().containsAll(tech.prerequisiteTechnologies))
                availableTechnologies.add(tech);
        }
    }

    private void openNewTechnology() {
        scienceStorage = 0;
        technologies.add(technologyInProgress);
        openedUnits.addAll(technologyInProgress.openingUnits);
        openedFeatures.addAll(technologyInProgress.openingFeatures);
        openedImprovements.addAll(technologyInProgress.openingImprovements);
        openedResources.addAll(technologyInProgress.openingResources);
        technologyInProgress = null;
    }

    private int calculateGoldPerTurn() {
        // TODO : implement
        return 0;
    }

    private int calculateSciencePerTurn() {
        int result = 0;
        for (City city : cities) {
            result += city.getNumberOfCitizen();
        }
        result += 3; // for capital
        return result;
    }

    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public User getUser() {
        return user;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }
    // TODO: 4/20/2022  getmap()

    public void adjustVisibility() {

        for (int i = 0; i < visibilityMap.map.size(); i++) {
            for (int j = 0; j < visibilityMap.map.get(0).size(); j++) {
                if (visibilityMap.map.get(i).get(j).getHexVisibility() != HexVisibility.FOG_OF_WAR) {
                    visibilityMap.map.get(i).get(j).setHexVisibility(HexVisibility.DETERMINED);
                }
            }
        }
        adjustVisibilityUnits();
        adjustVisibilityCities();
    }

    private void adjustVisibilityCities() {
        for (City city : cities) {
            for (Hex cityHex : city.getCityHexes()) {
                seeNeighbors(cityHex.getCoordinatesInMap().get('x'), cityHex.getCoordinatesInMap().get('y'));
            }
        }
    }

    private void adjustVisibilityUnits() {

        for (Unit unit : units) {
            int x = unit.getCoordinatesInMap().get('x');
            int y = unit.getCoordinatesInMap().get('y');
            visibilityMap.map.get(x).get(y / 2).setHexVisibility(HexVisibility.TRANSPARENT);
            seeNeighbors(x, y);
            for (NeighborHex neighborHex : NeighborHex.values()) {
                if (!(Game.getGame().map.map.get(x).get(y).getTerrain().name.equals(Terrain.HILL.name) ||
                        Game.getGame().map.map.get(x).get(y).getTerrain().name.equals(Terrain.MOUNTAIN.name) ||
                        Game.getGame().map.map.get(x).get(y).getFeature().name.equals(Feature.JUNGLE.name) ||
                        Game.getGame().map.map.get(x).get(y).getFeature().name.equals(Feature.DENSE_FOREST.name)))
                    seeNeighbors(x + neighborHex.xDiff, y + neighborHex.yDiff);
            }
        }
    }

    private void seeNeighbors(int x, int y) {
        for (NeighborHex neighborHex : NeighborHex.values()) {
            visibilityMap.map.get(x + neighborHex.xDiff).get((y + neighborHex.yDiff) / 2).setHexVisibility(HexVisibility.TRANSPARENT);
        }
    }

    public void setUp() {
        Random random = new Random();
        int xRand;
        int yRand;
        while (true) {
            xRand = random.nextInt(Game.getGame().getRows());
            yRand = random.nextInt(Game.getGame().getColumns());
            if (Game.getGame().map.map.get(xRand).get(yRand).getMovementPrice() >= 0 &
                    Game.getGame().map.map.get(xRand).get(yRand).getMilitaryUnit() == null &
                    Game.getGame().map.map.get(xRand).get(yRand).getCivilUnit() == null)
                break;
        }
        SettlerUnit settlerUnit = new SettlerUnit(xRand, yRand, this, 2, 5, UnitName.SETTLER);
        units.add(settlerUnit);
    }

    public int getScienceStorage() {
        return scienceStorage;
    }

    public void setScienceStorage(int scienceStorage) {
        this.scienceStorage = scienceStorage;
    }

    public int getSciencePerTurn() {
        return sciencePerTurn;
    }

    public void setSciencePerTurn(int sciencePerTurn) {
        this.sciencePerTurn = sciencePerTurn;
    }

    private int adjust(int xOry, boolean forX) {
        int ans = xOry - 2;
        if (ans < 0)
            return 0;
        if (forX) {
            if (xOry + 3 >= Game.getGame().getRows())

                return Game.getGame().getRows() - 6;
        } else {
            if (xOry + 3 >= Game.getGame().getColumns())
                return Game.getGame().getColumns() - 6;

        }
        return ans;
    }

    public String showMapOn(int xOfCenter, int yOfCenter) {
        int adjustedXOfUp = adjust(xOfCenter, true);
        int adjustedYOfLeft = adjust(yOfCenter, false);
        System.out.println("x:" + adjustedXOfUp + " y:" + adjustedYOfLeft);
        String[][] printMap = new String[60][120];
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 120; j++) {
                printMap[i][j] = GlobalThings.BLACK + '█';
            }
        }//6*6 map center on x center y center
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 3; j++) {
                int x = (i + 1) * GlobalThings.widthOfGrid / 2;
                int y = j * GlobalThings.lengthOfGrid * 2 + GlobalThings.lengthOfGrid;
                if (i % 2 == 1) y += GlobalThings.lengthOfGrid;
                for (int k = 0; k < GlobalThings.widthOfGrid / 2; k++) {
                    for (int l = 0; l <= 9 - k; l++) {
                        for (int z = y - l; z <= y + l; z++) {
                            if (z == y - l) printMap[x - k][z - 1] = "/";
                            if (z == y + l) printMap[x - k][z + 1] = "\\";
                            printMap[x - k][z] = GlobalThings.GREEN + '█';
                            printMap[x + k][z] = GlobalThings.GREEN + '█';
                        }
                    }
                }
                fillHexWithInfo(printMap, x, y, adjustedXOfUp + i / 2, adjustedYOfLeft + 2 * j + i % 2);
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 120; j++) {
                ans.append(printMap[i][j]);
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    private void fillHexWithInfo(String[][] printMap, int x, int y, int mapArrayX, int mapArrayY) {
        preliminaryInfo(printMap, x, y, mapArrayX, mapArrayY);


        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getCivilUnit() == null) {
            replaceText(printMap, x, y, -2, "CiU", "N/A", GlobalThings.RED);
        } else if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getCivilUnit() instanceof WorkerUnit) {
            replaceText(printMap, x, y, -2, "CiU", "WOR", GlobalThings.RED);
        } else {
            replaceText(printMap, x, y, -2, "CiU", "SET", GlobalThings.RED);
        }

        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getMilitaryUnit() == null) {
            replaceText(printMap, x, y, -1, "MiU", "N/A", GlobalThings.RED);
        } else {
            replaceText(printMap, x, y, -1, "MiU", Game.getGame().map.map.get(mapArrayX).get(mapArrayY)
                    .getMilitaryUnit().getName().toString().substring(0, 3), GlobalThings.RED);
        }

        replaceText(printMap, x, y, 0, "TER",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getTerrain().name.substring(0, 3), GlobalThings.YELLOW);


        replaceText(printMap, x, y, +1, "FET",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getFeature().name.substring(0, 3), GlobalThings.YELLOW);

        replaceText(printMap, x, y, +2, "RES",
                Game.getGame().map.map.get(mapArrayX).get(mapArrayY).getResource().name.substring(0, 3), GlobalThings.YELLOW);


        if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).isHasRoad()) {
            if (Game.getGame().map.map.get(mapArrayX).get(mapArrayY).isHasRailRoad())
                replaceText(printMap, x, y, +3, "ROD", "RAL", GlobalThings.BLACK);
            else
                replaceText(printMap, x, y, +3, "ROD", "ROD", GlobalThings.BLACK);
        } else {
            replaceText(printMap, x, y, +3, "ROD", "N/A", GlobalThings.WHITE_BACKGROUND + GlobalThings.BLACK);
        }
// TODO: 5/3/2022 river???


    }

    private void preliminaryInfo(String[][] printMap, int x, int y, int mapArrayX, int mapArrayY) {
        printMap[x + 4][y] = "-";
        printMap[x + 4][y + 1] = "-";
        printMap[x + 4][y + 2] = "-";
        printMap[x + 4][y + 3] = "-";
        printMap[x + 4][y + 4] = "-";
        printMap[x + 4][y + 5] = "-";
        printMap[x - 4][y] = "-";
        printMap[x - 4][y + 1] = "-";
        printMap[x - 4][y + 2] = "-";
        printMap[x - 4][y + 3] = "-";
        printMap[x - 4][y + 4] = "-";
        printMap[x - 4][y + 5] = "-";
        printMap[x + 4][y - 1] = "-";
        printMap[x + 4][y - 2] = "-";
        printMap[x + 4][y - 3] = "-";
        printMap[x + 4][y - 4] = "-";
        printMap[x + 4][y - 5] = "-";
        printMap[x - 4][y - 1] = "-";
        printMap[x - 4][y - 2] = "-";
        printMap[x - 4][y - 3] = "-";
        printMap[x - 4][y - 4] = "-";
        printMap[x - 4][y - 5] = "-";

        printMap[x - 3][y] = ",";
        printMap[x - 3][y - 1] = "";
        printMap[x - 3][y + 2] = "";
        printMap[x - 3][y - 2] = GlobalThings.BLUE + String.format("%02d", mapArrayX);
        printMap[x - 3][y + 1] = String.format("%02d", mapArrayY);
    }

    private void replaceText(String[][] map, int x, int y, int xDiff, String firstThree, String secondThree, String color) {
        map[x + xDiff][y] = ":";
        map[x + xDiff][y - 1] = "";
        map[x + xDiff][y - 2] = "";
        map[x + xDiff][y - 3] = color + firstThree;
        map[x + xDiff][y + 1] = secondThree;
        map[x + xDiff][y + 2] = "";
        map[x + xDiff][y + 3] = "";
    }


    public void setUser(User user) {
        this.user = user;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public Map getVisibilityMap() {
        return visibilityMap;
    }

    public void setVisibilityMap(Map visibilityMap) {
        this.visibilityMap = visibilityMap;
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<Technology> technologies) {
        this.technologies = technologies;
    }

    public Technology getTechnologyInProgress() {
        return technologyInProgress;
    }

    public void setTechnologyInProgress(Technology technologyInProgress) {
        this.technologyInProgress = technologyInProgress;
    }

    public ArrayList<UnitName> getOpenedUnits() {
        return openedUnits;
    }

    public void setOpenedUnits(ArrayList<UnitName> openedUnits) {
        this.openedUnits = openedUnits;
    }

    public ArrayList<Resource> getOpenedResources() {
        return openedResources;
    }

    public void setOpenedResources(ArrayList<Resource> openedResources) {
        this.openedResources = openedResources;
    }

    public ArrayList<Feature> getOpenedFeatures() {
        return openedFeatures;
    }

    public void setOpenedFeatures(ArrayList<Feature> openedFeatures) {
        this.openedFeatures = openedFeatures;
    }

    public ArrayList<Improvement> getOpenedImprovements() {
        return openedImprovements;
    }

    public void setOpenedImprovements(ArrayList<Improvement> openedImprovements) {
        this.openedImprovements = openedImprovements;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public ArrayList<Technology> getAvailableTechnologies() {
        return availableTechnologies;
    }

    public void setAvailableTechnologies(ArrayList<Technology> availableTechnologies) {
        this.availableTechnologies = availableTechnologies;
    }
}
