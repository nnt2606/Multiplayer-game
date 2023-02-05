package exceptions.roommanagement;

public class RoomNotExistException extends Exception {
    public RoomNotExistException() {
        super("ERROR: room not exists");
    }
}
