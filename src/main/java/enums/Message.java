package enums;

public enum Message {
    SUCCESS("successful"),
    LOGIN_ERROR("Incorrect username/password"),
    EMPTY_FIELDS("Please fill all the fields"),
    WEAK_PASSWORD("Entered password is weak"),
    USERNAME_EXISTS("Username is already taken");

    public final String message;
    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
