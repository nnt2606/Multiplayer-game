package message.scmessage.roommanagement;

import entity.user.User;
import message.scmessage.ScMessage;

public class ScLeaveRoom extends ScMessage {
    public int statusCode;
    public String userID;

    public ScLeaveRoom(int statusCode) {
        this.statusCode = statusCode;
    }

    public ScLeaveRoom(int statusCode, User user) {
        this.statusCode = statusCode;
        this.userID = user.getUserID().toString();
    }
}
