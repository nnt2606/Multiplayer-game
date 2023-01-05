package message.csmessage.roommanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import message.csmessage.CsMessage;
import session.Session;

public class CsGetRoomList extends CsMessage {
    public int number;

    public CsGetRoomList(int number) {
        super();
        this.number = number;
    }

    @JsonCreator
    public CsGetRoomList(@JsonProperty("generatedTime") long generatedTime, @JsonProperty("number") int number) {
        this.generatedTime = generatedTime;
        this.number = number;
    }

    @Override
    public void onMessage(Session session) {
        session.onGetRoomList(number);
    }
}
