package model;

import javafx.scene.image.Image;

public class GlobalThings {
    public static int mapHeight = 110;
    public static int mapWidth = 220;
    public static int lengthOfGrid = 16;
    public static int widthOfGrid = 8;
    public static final String RESET = "\033[0m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\033[0;36m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String WHITE = "\033[0;37m";
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String WHITE_BACKGROUND = "\033[47m";


    public static Image FOG_OF_WAR_IMAGE = new Image(GlobalThings.class.getResource("/tiles/Marsh1.png").toExternalForm());
}
