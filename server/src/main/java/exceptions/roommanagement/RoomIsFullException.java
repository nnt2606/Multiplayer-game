package exceptions.roommanagement;

public class RoomIsFullException extends Exception {
    public RoomIsFullException() {
        super("ERROR: Room is full");
    }
}
