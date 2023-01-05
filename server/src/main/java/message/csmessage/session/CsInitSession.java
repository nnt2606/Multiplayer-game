package message.csmessage.session;

import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsInitSession extends CsMessage {
    @Override
    public void onMessage(Session session) {
        session.onConnect();
    }
}