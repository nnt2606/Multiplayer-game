package exceptions.game;

public class NotEnoughPlayerException extends Exception {
    public NotEnoughPlayerException() {
        super("ERROR: Not enough player for the game to start");
    }
}
