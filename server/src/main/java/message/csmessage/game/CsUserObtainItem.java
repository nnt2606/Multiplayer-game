package message.csmessage.game;

import lombok.Getter;
import message.csmessage.CsMessage;
import session.Session;

@Getter
public class CsUserObtainItem extends CsMessage {
    public String itemID;

    @Override
    public void onMessage(Session session) {
        session.onUserObtainItem(this);
    }
}
