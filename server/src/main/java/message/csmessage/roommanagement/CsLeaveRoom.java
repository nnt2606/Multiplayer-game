package message.csmessage.roommanagement;

import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsLeaveRoom extends CsMessage {
    @Override
    public void onMessage(Session session) {
        session.onLeaveRoom();
    }
}
