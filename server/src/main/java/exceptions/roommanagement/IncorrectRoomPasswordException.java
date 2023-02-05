package exceptions.roommanagement;

public class IncorrectRoomPasswordException extends Exception {
    public IncorrectRoomPasswordException() {
        super("ERROR: Incorrect room password");
    }
}