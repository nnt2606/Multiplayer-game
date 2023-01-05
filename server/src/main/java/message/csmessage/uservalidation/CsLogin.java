package message.csmessage.uservalidation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsLogin extends CsMessage {
    public String userName;
    public String password;

    @JsonCreator
    public CsLogin(@JsonProperty("generatedTime") long generatedTime, @JsonProperty("userName") String userName, @JsonProperty("password") String password) {
        this.generatedTime = generatedTime;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void onMessage(Session session) {
        session.onLogin(userName, password);
    }
}
