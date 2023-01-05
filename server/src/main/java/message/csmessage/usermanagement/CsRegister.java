package message.csmessage.usermanagement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import message.csmessage.CsMessage;
import session.Session;

@AllArgsConstructor
public class CsRegister extends CsMessage {
    public String userName;
    public String password;
    public String retypePassword;

    @JsonCreator
    public CsRegister(@JsonProperty("generatedTime") long generatedTime,
                      @JsonProperty("userName") String userName,
                      @JsonProperty("password") String password,
                      @JsonProperty("retypePassword") String retypePassword) {
        this.generatedTime = generatedTime;
        this.userName = userName;
        this.password = password;
        this.retypePassword = retypePassword;
    }

    @Override
    public void onMessage(Session session) {
        session.onRegister(userName, password, retypePassword);
    }
}
