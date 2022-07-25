package server.enums;

import java.util.Random;

public enum Avatar {
    AVATAR_1,
    AVATAR_2,
    AVATAR_3,
    AVATAR_4,
    AVATAR_5,
    AVATAR_6,
    AVATAR_7,
    AVATAR_8,
    AVATAR_9,
    AVATAR_10;

    public static Avatar getRandomAvatar() {
        Random random = new Random();
        return values()[random.nextInt(values().length-1)];
    }
}
