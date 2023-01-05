package message.csmessage.game;

import message.csmessage.CsMessage;
import session.Session;

public class CsGameStart extends CsMessage {
    @Override
    public void onMessage(Session session) {
        session.onGameStart();
    }
}
