package message.scmessage.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import message.scmessage.ScMessage;

@AllArgsConstructor
@Getter
public class ScChat extends ScMessage {
    private String message;
    private String userID;
}
