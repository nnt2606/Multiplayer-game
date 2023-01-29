package entity.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.user.User;
import exceptions.roommanagement.IncorrectRoomPasswordException;
import exceptions.roommanagement.RoomIsFullException;
import lombok.Getter;
import message.scmessage.ScMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Room {
    private static int MAX_MEMBER_NUMBER = 4;
    private UUID roomID;
    private String roomName;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private List<User> roomMembers = new ArrayList<User>();
    private boolean hasPass = true;

    public Room(String roomName, String password) {
        this.roomID = UUID.randomUUID();
        this.roomName = roomName;
        if (password == null || password.length() == 0) {
            hasPass = false;
        }
        this.password = password;
        RoomManager.getInstance().addRoom(this);
    }

    public void addUser(User user, String password) throws RoomIsFullException, IncorrectRoomPasswordException {
        if (hasPass) {
            if (!this.password.equals(password)) {
                throw new IncorrectRoomPasswordException();
            }
        }
        if (this.roomMembers.size() < 4) {
            this.roomMembers.add(user);
        } else {
            throw new RoomIsFullException();
        }
    }

    public void removeUser(User user) {
        this.roomMembers.remove(user);
        if (this.roomMembers.size() == 0) {
            RoomManager.getInstance().removeRoom(this);
        }
    }

    public void forwardMessage(ScMessage message) {
        for (User user : roomMembers) {
            user.forwardMessage(message);
        }
    }

    public int getNumberOfMembers() {
        return this.roomMembers.size();
    }

    public void close() {
//        this.roomMembers.removeAll(this.roomMembers);
        RoomManager.getInstance().removeRoom(this);
    }

    @JsonIgnore
    public User getRoomMaster() {
        return roomMembers.get(0);
    }

    @JsonIgnore
    public List<String> getBasicRoomMembers() {
        List<String> basicRoomMembers = new ArrayList<String>();
        for (User user : this.roomMembers) {
            basicRoomMembers.add(user.getUserName());
        }
        return basicRoomMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Room) {
            Room room = (Room) o;
            if (room.getRoomID().toString().equals(this.roomID.toString())) {
                return true;
            }
            return false;
        }
        return false;
    }
}
