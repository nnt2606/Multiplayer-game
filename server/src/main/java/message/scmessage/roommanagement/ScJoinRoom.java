package message.scmessage.roommanagement;

import entity.user.User;
import message.scmessage.ScMessage;

public class ScJoinRoom extends ScMessage {
    public int statusCode;
    public User user;

    public ScJoinRoom(int statusCode) {
        this.statusCode = statusCode;
    }

    public ScJoinRoom(int statusCode, User user) {
        this.statusCode = statusCode;
        this.user = user;
    }
}
