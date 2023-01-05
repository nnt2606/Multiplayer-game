package message.csmessage.usermanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsChangeAvatar extends CsMessage {
    public String avatar;

    @JsonCreator
    public CsChangeAvatar(@JsonProperty("generatedTime") long generatedTime, @JsonProperty("avatar") String avatar) {
        this.generatedTime = generatedTime;
        this.avatar = avatar;
    }

    @Override
    public void onMessage(Session session) {
        session.onChangeAvatar(avatar);
    }
}
