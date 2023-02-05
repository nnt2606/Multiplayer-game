package exceptions.game;

public class InvalidItemObtainedException extends Exception {
    public InvalidItemObtainedException() {
        super("ERROR: User obtain non exist item");
    }
}
