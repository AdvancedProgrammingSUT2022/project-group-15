package enums;

import java.util.ArrayList;

public enum Building {
    Barracks(80,1,null),
    Granary(100,1,null),
    Library(80,1,null),
    Monument(60,1,null),
    Walls(100,1,null),
    Watermill(120,2,null),
    Armory(130,3,null),
    BurialTomb(120,0,null),
    Circus(150,3,null),
    Colosseum(150,3,null),
    Courthouse(200,5,null),
    Stable(100,1,null),
    Temple(120,2,Monument),
    Castle(200,3,Walls),
    Forge(150,2,null),
    Market(120,0,null),
    Mint(120,0,null),
    Monastery(120,2,null),
    University(200,3,Library),
    Workshop(100,2,null),
    Bank(350,3,Market),
    MilitaryAcademy(350,3,Barracks),
    OperaHouse(220,3,Temple),
    Museum(350,3,OperaHouse),
    PublicSchool(350,3,University),
    SatrapCourt(220,0,Market),
    Theater(300,5,Colosseum),
    Windmill(180,2,null),
    Arsenal(350,3,MilitaryAcademy),
    BroadcastTower(600,3,Museum),
    Factory(300,3,null),
    Hospital(400,2,null),
    MilitaryBase(450,4,Castle),
    StockExchange(650,0,Bank),
    ;


    public int productionCost;
    public int maintenanceCost;
    public Building prerequisiteBuilding;

    Building(int productionCost, int maintenanceCost, Building prerequisiteBuildings) {
        this.productionCost = productionCost;
        this.maintenanceCost = maintenanceCost;
        this.prerequisiteBuilding = prerequisiteBuildings;
    }
}
