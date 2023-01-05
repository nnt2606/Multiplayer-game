package message.scmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.room.Room;
import entity.room.RoomManager;
import entity.user.User;
import exceptions.roommanagement.RoomNotExistException;
import lombok.AllArgsConstructor;
import message.scmessage.ScMessage;

import java.util.List;

@AllArgsConstructor
public class ScGetRoom extends ScMessage {
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String roomName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String roomID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String roomMaster;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<User> userList;

    public ScGetRoom(String roomID) {
        try {
            Room room = RoomManager.getInstance().getRoom(roomID);
            this.statusCode = 200;
            this.roomID = room.getRoomID().toString();
            this.roomName = room.getRoomName();
            this.roomMaster = room.getRoomMaster().getUserID().toString();
            this.userList = room.getRoomMembers();
        } catch (RoomNotExistException e) {
            this.statusCode = 400;
            e.printStackTrace();
        }
    }
}
