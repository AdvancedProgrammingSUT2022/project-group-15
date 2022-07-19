package enums;

public enum Building {
    BARRACKS("Barracks", 80,1,null),
    GRANARY("Granary",100,1,null),
    LIBRARY("Library",80,1,null),
    MONUMENT("Monument",60,1,null),
    WALLS("Walls",100,1,null),
    WATERMILL("Watermill",120,2,null),
    ARMORY("Armory",130,3,null),
    BURIAL_TOMB("Burial Tomb",120,0,null),
    CIRCUS("Circus",150,3,null),
    COLOSSEUM("Colosseum",150,3,null),
    COURTHOUSE("Courthouse",200,5,null),
    STABLE("Stable",100,1,null),
    TEMPLE("Temple",120,2,MONUMENT),
    CASTLE("Castle",200,3,WALLS),
    FORGE("Forge",150,2,null),
    GARDEN("Garden",150, 2, null),
    MARKET("Market",120,0,null),
    MINT("Mint",120,0,null),
    MONASTERY("Monastery",120,2,null),
    UNIVERSITY("University",200,3,LIBRARY),
    WORKSHOP("Workshop",100,2,null),
    BANK("Bank",350,3,MARKET),
    MILITARY_ACADEMY("Military Academy",350,3,BARRACKS),
    OPERA_HOUSE("Opera House",220,3,TEMPLE),
    MUSEUM("Museum",350,3, OPERA_HOUSE),
    PUBLIC_SCHOOL("Public School",350,3,UNIVERSITY),
    SATRAP_COURT("Satrap's Court",220,0,MARKET),
    THEATER("Theater",300,5,COLOSSEUM),
    WINDMILL("Windmill",180,2,null),
    ARSENAL("Arsenal",350,3, MILITARY_ACADEMY),
    BROADCAST_TOWER("Broadcast Tower",600,3,MUSEUM),
    FACTORY("Factory",300,3,null),
    HOSPITAL("Hospital",400,2,null),
    MILITARY_BASE("Military Base",450,4,CASTLE),
    STOCK_EXCHANGE("Stock Exchange",650,0,BANK);

    public final String name;
    public final int productionCost;
    public final int maintenanceCost;
    public final Building prerequisiteBuilding;

    Building(String name, int productionCost, int maintenanceCost, Building prerequisiteBuildings) {
        this.name = name;
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
