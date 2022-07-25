package client.enums;

public enum Ruins {
    addPerson("a person was added to your capital",1),
    addGold("you received 30 golds",2),
    addScience("you received 30 science",3),
    addFood("capital received 5 food",4);

    public final String message;
    public final int code;

    Ruins(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
