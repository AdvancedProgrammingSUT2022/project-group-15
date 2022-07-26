package server.model;



import server.enums.*;
import server.model.unit.CivilUnit;
import server.model.unit.SettlerUnit;
import server.model.unit.Unit;
import server.model.unit.WorkerUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;


public class Civilization {
    transient private User user;
    private String username;
    private boolean isYourTurn;
    private transient Map visibilityMap;
    private transient ArrayList<Technology> technologies = new ArrayList<>();
    private transient ArrayList<Technology> availableTechnologies = new ArrayList<>();
    private transient Technology technologyInProgress = Technology.AGRICULTURE;
    private ArrayList<UnitName> openedUnits = new ArrayList<>(Arrays.asList(UnitName.WORKER, UnitName.SETTLER, UnitName.WARRIOR, UnitName.SCOUT));
    private ArrayList<Resource> openedResources = new ArrayList<>();
    private ArrayList<Feature> openedFeatures = new ArrayList<>();
    private ArrayList<Improvement> openedImprovements = new ArrayList<>();
    private ArrayList<Building> openedBuildings = new ArrayList<>(Arrays.asList(Building.MONUMENT));
    private ArrayList<Resource> strategicResources = new ArrayList<>();
    private HashSet<Resource> luxuryResources = new HashSet<>();
    private City capital;
    private transient ArrayList<Unit> units = new ArrayList<>();
    private transient ArrayList<City> cities = new ArrayList<>();
    private int goldStorage = 30;
    private int scienceStorage = 0;
    private int sciencePerTurn = 0;
    private int happiness = 0;
    private int roadMaintenance = 0;
    private int cityBuildingHappiness;
    private ArrayList<Civilization> enemies = new ArrayList<>();


    public Civilization(User user) {
        this.user = user;
        this.username = user.getUsername();
        visibilityMap = Game.getGame().map.clone();
        strategicResources.add(Resource.NULL);
    }

    public void nextTurn() {
        for (Unit unit : units) {
            unit.updateUnit();
        }
        for (City city : cities) {
            city.moveToNextTurn();
        }
        if (goldStorage + calculateGoldPerTurn() >= 0)
            goldStorage += calculateGoldPerTurn();
        else {
            goldStorage = 0;
            sciencePerTurn += goldStorage + calculateGoldPerTurn();
        }
        scienceStorage += sciencePerTurn;
        if (technologyInProgress != null && scienceStorage >= technologyInProgress.cost) {
            openNewTechnology();
            updateAvailableTechnologies();
        }
        sciencePerTurn = calculateSciencePerTurn();
        happiness = calculateHappiness();
    }

    private int calculateHappiness() {
        int ans = 1;
        ans -= cities.size();
        for (City city : cities) {
            ans -= city.getNumberOfCitizen();
        }
        ans /= 3;
        ans += 4 * luxuryResources.size();
        ans += cityBuildingHappiness;
        return ans;
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
        openedImprovements.addAll(technologyInProgress.openingImprovements);
        openedResources.addAll(technologyInProgress.openingResources);
        openedBuildings.addAll(technologyInProgress.openingBuildings);
        technologyInProgress = null;
        for (City city : cities) {
            city.updateAvailableBuildings();
        }
    }

    private int calculateGoldPerTurn() {
        int ans = 0;
        for (City city : cities) {
            ans += city.getGoldPerTurn();
        }
        ans -= units.size();
        ans -= roadMaintenance;
        return ans;
    }

    private int calculateSciencePerTurn() {
        int result = 0;
        for (City city : cities) {
            result += city.getSciencePerTurn();
        }
        result += 3; // for capital
        return result;
    }

    public void deleteUnit(Unit unit, boolean isSelling) {
        if (unit.getName().equals(UnitName.CITYUNIT)) {
            Hex hex = Game.getGame().map.map.get(unit.getCoordinatesInMap().get('x') / 2).get(unit.getCoordinatesInMap().get('y'));
            City goingToDestroy = hex.getCity();
            this.cities.remove(goingToDestroy);
            if (capital == goingToDestroy) {
                if (cities.size() == 0)
                    lose();
                else
                    capital = cities.get(0);
            }
            for (Hex cityHex : goingToDestroy.getCityHexes()) {
                cityHex.setCity(null);
            }
            if (hex.getMilitaryUnit() != null)
                deleteUnit(hex.getMilitaryUnit(), false);
            if (hex.getCivilUnit() != null)
                deleteUnit(hex.getCivilUnit(), false);
            return;
        }
        if (isSelling) {
            goldStorage += unit.getCost() / 5;
        }
        if (unit instanceof CivilUnit)
            Game.getGame().map.map.get(unit.getCoordinatesInMap().get('x') / 2).get(unit.getCoordinatesInMap().get('y')).setCivilUnit(null);
        else
            Game.getGame().map.map.get(unit.getCoordinatesInMap().get('x') / 2).get(unit.getCoordinatesInMap().get('y')).setMilitaryUnit(null);
        units.remove(unit);
    }

    private void lose() {
        for (Unit unit : units) {
            deleteUnit(unit,false);
        }
        Game.getGame().getCivilizations().remove(this);
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
            seeNeighbors(x, y);
            for (NeighborHex neighborHex : NeighborHex.values()) {
                if (visibilityMap.validCoordinateInArray((x + neighborHex.xDiff) / 2, y + neighborHex.yDiff))
                    if (!(Game.getGame().map.map.get((x + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff).getTerrain().name.equals(Terrain.HILL.name) ||
                            Game.getGame().map.map.get((x + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff).getTerrain().name.equals(Terrain.MOUNTAIN.name) ||
                            Game.getGame().map.map.get((x + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff).getFeature().name.equals(Feature.JUNGLE.name) ||
                            Game.getGame().map.map.get((x + neighborHex.xDiff) / 2).get(y + neighborHex.yDiff).getFeature().name.equals(Feature.DENSE_FOREST.name)))
                        seeNeighbors(x + neighborHex.xDiff, y + neighborHex.yDiff);
            }
        }
    }

    private void updateHexWithMainMap(int xInArr, int yInArr) {
        Hex newHex = Game.getGame().map.map.get(xInArr).get(yInArr).clone();
        newHex.setHexVisibility(HexVisibility.TRANSPARENT);
        visibilityMap.map.get(xInArr).set(yInArr, newHex);
    }

    private void seeNeighbors(int x, int y) {
        updateHexWithMainMap(x / 2, y);
        for (NeighborHex neighborHex : NeighborHex.values()) {
            if (visibilityMap.validCoordinateInArray((x + neighborHex.xDiff) / 2, (y + neighborHex.yDiff)))
                updateHexWithMainMap((x + neighborHex.xDiff) / 2, y + neighborHex.yDiff);
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
        new SettlerUnit(xRand, yRand, this, UnitName.SETTLER);
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

    private int adjustXAndY(int xOry, boolean forX) {
        if (!forX & xOry % 2 == 1)
            xOry -= 1;
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
        this.adjustVisibility();
        int adjustedXOfUp = adjustXAndY(xOfCenter, true);
        int adjustedYOfLeft = adjustXAndY(yOfCenter, false);
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
        ans.append(GlobalThings.RESET);
        return ans.toString();
    }

    private void fillHexWithInfo(String[][] printMap, int x, int y, int mapArrayX, int mapArrayY) {
        preliminaryInfo(printMap, x, y, mapArrayX, mapArrayY);


        if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getHexVisibility().equals(HexVisibility.FOG_OF_WAR))
            return;

        if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getCity() != null && getVisibilityMap().isCenterOfCity(mapArrayX, mapArrayY)) {
            replaceText(printMap, x, y, -3, "CTY",
                    this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getCity().getName().substring(0, 3), GlobalThings.BLUE);
        }

        if (!this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getHexVisibility().equals(HexVisibility.DETERMINED)) {
            if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getCivilUnit() == null) {
                replaceText(printMap, x, y, -2, "CiU", "N/A", GlobalThings.RED);
            } else if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getCivilUnit() instanceof WorkerUnit) {
                replaceText(printMap, x, y, -2, "CiU", "WOR", GlobalThings.RED);
            } else {
                replaceText(printMap, x, y, -2, "CiU", "SET", GlobalThings.RED);
            }


            if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getMilitaryUnit() == null) {
                replaceText(printMap, x, y, -1, "MiU", "N/A", GlobalThings.RED);
            } else {
                replaceText(printMap, x, y, -1, "MiU", Game.getGame().map.map.get(mapArrayX).get(mapArrayY)
                        .getMilitaryUnit().getName().toString().substring(0, 3), GlobalThings.RED);
            }

        }

        replaceText(printMap, x, y, 0, "TER",
                this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getTerrain().name.substring(0, 3), GlobalThings.YELLOW);


        replaceText(printMap, x, y, +1, "FET",
                this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getFeature().name.substring(0, 3), GlobalThings.YELLOW);

        if (!this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getResource().type.equals("strategic") ||
                this.openedResources.contains(this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getResource()))
            replaceText(printMap, x, y, +2, "RES",
                    this.visibilityMap.map.get(mapArrayX).get(mapArrayY).getResource().name.substring(0, 3), GlobalThings.YELLOW);
        else
            replaceText(printMap, x, y, +2, "RES", "nul", GlobalThings.YELLOW);


        String background;
        if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).doesHaveRiver())
            background = GlobalThings.BLUE_BACKGROUND;
        else
            background = GlobalThings.WHITE_BACKGROUND;
        if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).isHasRoad()) {
            if (this.visibilityMap.map.get(mapArrayX).get(mapArrayY).isHasRailRoad())
                replaceText(printMap, x, y, +3, "ROD", "RAL", background + GlobalThings.BLACK);
            else
                replaceText(printMap, x, y, +3, "ROD", "ROD", background + GlobalThings.BLACK);
        } else {
            replaceText(printMap, x, y, +3, "ROD", "N/A", background + GlobalThings.BLACK);
        }


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

    public void payMoney(int money) {
        this.goldStorage -= money;
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

    public ArrayList<Building> getOpenedBuildings() {
        return openedBuildings;
    }

    public HashSet<Resource> getLuxuryResources() {
        return luxuryResources;
    }


    public ArrayList<Improvement> getOpenedImprovements() {
        return openedImprovements;
    }

    public void setOpenedImprovements(ArrayList<Improvement> openedImprovements) {
        this.openedImprovements = openedImprovements;
    }

    public ArrayList<Resource> getStrategicResources() {
        return strategicResources;
    }

    public void setStrategicResources(ArrayList<Resource> strategicResources) {
        this.strategicResources = strategicResources;
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

    public ArrayList<Civilization> getEnemies() {
        return enemies;
    }

    public int getPopulation() {
        int ans = 0;
        for (City city : cities) {
            ans += city.getNumberOfCitizen();
        }
        return ans;
    }

    public int getArea() {
        int result = 0;
        for (City city : cities) {
            result += city.getCityHexes().size();
        }
        return result;
    }

    public int getRoadMaintenance() {
        return roadMaintenance;
    }

    public void setRoadMaintenance(int roadMaintenance) {
        this.roadMaintenance = roadMaintenance;
    }

    public void addHappinessFromBuilding(int i) {
        cityBuildingHappiness += i;
    }

    public String getUsername() {
        return username;
    }
}
