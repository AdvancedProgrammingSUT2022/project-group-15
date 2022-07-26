package client.model;

import com.google.gson.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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


    public static Image FOG_OF_WAR_IMAGE = new Image(GlobalThings.class.getResource("/tiles/fog of war.png").toExternalForm());
    public static Image RUINS_IMAGE = new Image(GlobalThings.class.getResource("/icons/ruins.png").toExternalForm());
    public static Image CITY_IMAGE = new Image(GlobalThings.class.getResource("/tiles/city.png").toExternalForm());
    public static Image CIVILIZATION_IMAGE = new Image(GlobalThings.class.getResource("/icons/Civ-5-icon.png").toExternalForm());

    public static final BooleanProperty musicOn = new SimpleBooleanProperty(true);
    public static Clip clip;

    public static BooleanProperty musicOnProperty() {
        return musicOn;
    }

    public static void setMusicOn(boolean musicOn) {
        GlobalThings.musicOn.set(musicOn);
    }

    static {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./src/main/resources/backgroundMusic/Background Music.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            playMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playMusic() {
        setMusicOn(true);
        clip.start();
    }

    public static void pauseMusic() {
        setMusicOn(false);
        clip.stop();
    }

    public static Gson gson = createMyGson();

    /**
     * creates a Gson that has been customized
     *
     * @return the customized Gson object
     * @author Parsa
     */
    private static Gson createMyGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(),
                        DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(formatter.format(localDateTime));
            }
        }).setPrettyPrinting().disableHtmlEscaping().create();
    }
}
