package message.csmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsGetRoom extends CsMessage {
    public String roomID;

    @JsonCreator
    public CsGetRoom(@JsonProperty("generatedTime") long generatedTime, @JsonProperty("roomID") String roomID) {
        this.generatedTime = generatedTime;
        this.roomID = roomID;
    }

    @Override
    public void onMessage(Session session) {
        session.onGetRoom(roomID);
    }
}
