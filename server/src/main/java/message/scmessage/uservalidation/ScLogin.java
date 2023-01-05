package message.scmessage.uservalidation;

import com.fasterxml.jackson.annotation.JsonInclude;
import message.scmessage.ScMessage;

public class ScLogin extends ScMessage {
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String userID;

    public ScLogin(String UID, int statusCode) {
        if (UID != null) {
            this.statusCode = statusCode;
            this.userID = UID;
        } else {
            this.statusCode = statusCode;
        }
    }
}
