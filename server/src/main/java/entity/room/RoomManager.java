package entity.room;

import exceptions.roommanagement.RoomNotExistException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomManager {
    private static RoomManager instance = new RoomManager();
    private List<Room> roomList = new ArrayList<Room>();

    private RoomManager() {
    }

    public static RoomManager getInstance() {
        return instance;
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void removeRoom(Room room) {
        roomList.remove(room);
    }

    public Room getRoom(String roomID) throws RoomNotExistException {
        for (Room room : roomList) {
            if (room.getRoomID().toString().equals(roomID)) {
                return room;
            }
        }
        throw new RoomNotExistException();
    }

    public List<Room> getRoomList(int number) {
        Collections.shuffle(roomList);
        int size = Math.min(number, this.roomList.size());
        return this.roomList.subList(0, size);
    }
}
