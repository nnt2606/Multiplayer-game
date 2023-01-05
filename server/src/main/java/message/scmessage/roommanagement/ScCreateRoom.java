package message.scmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import message.scmessage.ScMessage;

public class ScCreateRoom extends ScMessage {
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String roomID;

    public ScCreateRoom(String roomID, int statusCode) {
        if (roomID != null) {
            this.statusCode = statusCode;
            this.roomID = roomID;
        } else {
            this.statusCode = statusCode;
        }
    }
}
