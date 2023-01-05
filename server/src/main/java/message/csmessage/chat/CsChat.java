package message.csmessage.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsChat extends CsMessage {
    public String message;

    @JsonCreator
    public CsChat(@JsonProperty("message") String message, @JsonProperty("generatedTime") long generatedTime) {
        this.message = message;
        this.generatedTime = generatedTime;
    }

    @Override
    public void onMessage(Session session) {
        session.onChat(this);
    }
}
