package message.scmessage.roommanagement;

import entity.room.Room;
import entity.room.RoomManager;
import message.scmessage.ScMessage;

import java.util.List;

public class ScGetRoomList extends ScMessage {
    public List<Room> roomList;

    public ScGetRoomList(int number) {
        this.roomList = RoomManager.getInstance().getRoomList(number);
    }
}
