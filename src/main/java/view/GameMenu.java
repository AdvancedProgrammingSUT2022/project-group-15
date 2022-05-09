package view;

import controller.GameMenuController;

import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private final GameMenuController controller = new GameMenuController();

    /**
     * @author Parsa
     */
    @Override
    protected String checkCommand(String command) {
        Matcher matcher;
        if (command.equals("menu show-current")) {
            System.out.println("Game Menu");
        } else if (command.equals("menu exit")) {
            System.out.println("you are in the main menu");
            return "main menu";
        } else if (command.startsWith("menu enter")) {
            System.out.println("menu navigation is not possible");
        } else if ((matcher = getMatcher(command, "^info (?<part>\\w+)$")) != null) {
            String part = matcher.group("part");
            switch (part) {
                case "research":
                    System.out.println(controller.showTechnologyInfo());
                    break;
                case "units":
                    System.out.println(controller.showUnitsPanel());
                    break;
                case "cities":
                    System.out.println(controller.showCitiesPanel());
                    break;
                case "diplomacy":
                    System.out.println(controller.showDiplomacyPanel());
                    break;
                case "victory":
                    System.out.println(controller.showVictoryPanel());
                    break;
                case "demographics":
                    System.out.println(controller.showDemographicsPanel());
                    break;
                case "notifications":
                    System.out.println(controller.showNotificationHistory());
                    break;
                case "military":
                    System.out.println(controller.showMilitaryPanel());
                    break;
                case "economic":
                    System.out.println(controller.showEconomyPanel());
                    break;
                case "deals":
                    System.out.println(controller.showDealsPanel());
                    break;
                default:
                    System.out.println("invalid command!");
                    break;
            }
        } else if ((matcher = getMatcher(command, "^buy new technology : (?<tech>\\w+)$")) != null) {
            String technologyName = matcher.group("tech");
            System.out.println(controller.buyNewTechnology(technologyName));
        } else if ((matcher = getMatcher(command, "^select unit combat (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.selectMilitaryUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = getMatcher(command, "^select unit noncombat (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.selectCivilUnit(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = getMatcher(command, "^select city (?<cityName>\\w+)$")) != null) {
            System.out.println(controller.selectCity(matcher.group("cityName")));
        } else if ((matcher = getMatcher(command, "^select city (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.selectCity(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = getMatcher(command, "^unit moveto (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.moveSelectedUnitTo(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if (command.equals("unit sleep")) {
            System.out.println(controller.sleepSelectedUnit());
        } else if (command.equals("unit alert")) {
            System.out.println(controller.alertSelectedUnit());
        } else if (command.equals("unit fortify")) {
            System.out.println(controller.fortifySelectedUnit());
        } else if (command.equals("unit garrison")) {
            System.out.println(controller.garrisonSelectedUnit());
        } else if (command.equals("unit setup ranged")) {
            System.out.println(controller.setupRangedSelectedUnit());
        } else if (command.equals("unit fortify heal")) {
            System.out.println(controller.fortifySelectedUnitTillHeal());
        } else if (command.equals("unit found city")) {
            System.out.println(controller.foundCity());
        } else if (command.equals("unit cancel mission")) {
            System.out.println(controller.cancelSelectedUnitMission());
        } else if (command.equals("unit wake")) {
            System.out.println(controller.wakeUpSelectedUnit());
        } else if (command.equals("unit delete")) {
            System.out.println(controller.deleteSelectedUnit());
        } else if ((matcher = getMatcher(command, "^unit attack (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.attackTo(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = getMatcher(command, "^unit build (?<improvement>\\w+)$")) != null) {
            System.out.println(controller.buildImprovement(matcher.group("improvement")));
        } else if (command.equals("unit remove jungle")) {
            System.out.println(controller.removeJungle());
        } else if (command.equals("unit remove route")) {
            System.out.println(controller.removeRoute());
        } else if (command.equals("unit repair")) {
            System.out.println(controller.repair());
        }else if ((matcher = getMatcher(command, "^remove citizen from work on (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.removeCitizenFromWork(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if ((matcher = getMatcher(command, "^lock citizen on (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.lockCitizenToHex(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
        } else if (command.equals("show map")) {
            System.out.println(controller.showMap());
            return "continue";
        } else if ((matcher = getMatcher(command, "^map move (?<direction>\\w+) (?<amount>\\d+)$")) != null) {
            System.out.println(controller.moveMap(matcher.group("direction"), Integer.parseInt(matcher.group("amount"))));
            return "continue";
        } else if ((matcher = getMatcher(command, "^map show (?<x>\\d+) (?<y>\\d+)$")) != null) {
            System.out.println(controller.showMapOnPosition(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            return "continue";
        } else if ((matcher = getMatcher(command, "^map show (?<cityName>\\w+)$")) != null) {
            System.out.println(controller.showMapOnCity(matcher.group("cityName")));
            return "continue";
        } else if (command.equals("next turn")) {
            System.out.println(controller.changeTurn());
        } else {
            System.out.println("invalid command!");
        }
        System.out.println(controller.moveMap("null", 0)); // shows map after each command
        return "continue";
    }
}
