package server.enums;

import javafx.scene.image.Image;

import java.util.Random;

public enum Avatar {
    AVATAR_1("/avatars/avatar_1.png"),
    AVATAR_2("/avatars/avatar_2.png"),
    AVATAR_3("/avatars/avatar_3.png"),
    AVATAR_4("/avatars/avatar_4.png"),
    AVATAR_5("/avatars/avatar_5.png"),
    AVATAR_6("/avatars/avatar_6.png"),
    AVATAR_7("/avatars/avatar_7.png"),
    AVATAR_8("/avatars/avatar_8.png"),
    AVATAR_9("/avatars/avatar_9.png"),
    AVATAR_10("/avatars/avatar_10.png");
    public final Image image;

    Avatar(String path) {
        this.image = new Image(getClass().getResource(path).toExternalForm());
    }

    public static Avatar getRandomAvatar() {
        Random random = new Random();
        return values()[random.nextInt(values().length-1)];
    }
}
