package enums;

import java.util.ArrayList;

public enum Building {
    BARRACKS(80,1,null),
    GRANARY(100,1,null),
    LIBRARY(80,1,null),
    MONUMENT(60,1,null),
    WALLS(100,1,null),
    WATERMILL(120,2,null),
    ARMORY(130,3,null),
    BURIALTOMB(120,0,null),
    CIRCUS(150,3,null),
    COLOSSEUM(150,3,null),
    COURTHOUSE(200,5,null),
    STABLE(100,1,null),
    TEMPLE(120,2,MONUMENT),
    CASTLE(200,3,WALLS),
    FORGE(150,2,null),
    GARDEN(150, 2, null),
    MARKET(120,0,null),
    MINT(120,0,null),
    MONASTERY(120,2,null),
    UNIVERSITY(200,3,LIBRARY),
    WORKSHOP(100,2,null),
    BANK(350,3,MARKET),
    MILITARYACADEMY(350,3,BARRACKS),
    OPERAHOUSE(220,3,TEMPLE),
    MUSEUM(350,3,OPERAHOUSE),
    PUBLICSCHOOL(350,3,UNIVERSITY),
    SATRAPCOURT(220,0,MARKET),
    THEATER(300,5,COLOSSEUM),
    WINDMILL(180,2,null),
    ARSENAL(350,3,MILITARYACADEMY),
    BROADCASTTOWER(600,3,MUSEUM),
    FACTORY(300,3,null),
    HOSPITAL(400,2,null),
    MILITARYBASE(450,4,CASTLE),
    STOCKEXCHANGE(650,0,BANK),
    ;


    public final int productionCost;
    public final int maintenanceCost;
    public final Building prerequisiteBuilding;

    Building(int productionCost, int maintenanceCost, Building prerequisiteBuildings) {
        this.productionCost = productionCost;
        this.maintenanceCost = maintenanceCost;
        this.prerequisiteBuilding = prerequisiteBuildings;
    }

    public static Building getBuildingByName(String name) {
        for (Building building : Building.values()) {
            if (name.equalsIgnoreCase(building.name()))
                return building;
        }
        return null;
    }
}
