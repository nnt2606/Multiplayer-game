package message.csmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsJoinRoom extends CsMessage {
    public String roomID;
    public String password;

    public CsJoinRoom(String roomID) {
        this.roomID = roomID;
    }

    @JsonCreator
    public CsJoinRoom(@JsonProperty("generatedTime") long generatedTime,
                      @JsonProperty("roomID") String roomID,
                      @JsonProperty("password") String password) {
        this.generatedTime = generatedTime;
        this.roomID = roomID;
        this.password = password;
    }

    @Override
    public void onMessage(Session session) {
        session.onJoinRoom(roomID, password);
    }
}