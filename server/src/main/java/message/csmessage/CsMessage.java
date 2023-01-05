package message.csmessage;

import lombok.AllArgsConstructor;
import message.Message;
import session.Session;

@AllArgsConstructor
public abstract class CsMessage extends Message {
    protected boolean broadCast = false;

    public CsMessage() {
        super();
    }

    public abstract void onMessage(Session session);
}