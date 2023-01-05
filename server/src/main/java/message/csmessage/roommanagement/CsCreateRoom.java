package message.csmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsCreateRoom extends CsMessage {
    public String roomName;
    public String password;
//    public String userID;
//    public String avatar;

    public CsCreateRoom(String roomName) {
        this.roomName = roomName;
    }

    @JsonCreator
    public CsCreateRoom(@JsonProperty("generatedTime") long generatedTime,
                        @JsonProperty("password") String password,
                        @JsonProperty("roomName") String roomName) {
        this.generatedTime = generatedTime;
        this.password = password;
        this.roomName = roomName;
    }

    @Override
    public void onMessage(Session session) {
        session.onCreateRoom(roomName, password);
    }
}