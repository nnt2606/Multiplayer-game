package message.csmessage.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import message.csmessage.CsMessage;
import session.Session;

public class CsCharacterState extends CsMessage {
    public String id;
    public float locationX;
    public float locationY;
    public float directionX;
    public float directionY;
    public int state;

    public void onMessage(Session session) {
        session.onCharacterState(this);
    }

    @JsonIgnore
    public String getUserId() {
        return id;
    }
}
