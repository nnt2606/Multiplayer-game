package message.scmessage.usermanagement;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.user.User;
import message.scmessage.ScMessage;

public class ScRegister extends ScMessage {
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String UID;

    public ScRegister(User user, int statusCode) {
        this.statusCode = statusCode;
        if (user != null) {
            this.UID = user.getUserID().toString();
        }
    }
}