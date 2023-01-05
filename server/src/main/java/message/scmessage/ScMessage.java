package message.scmessage;

import lombok.Getter;
import message.Message;

@Getter
public abstract class ScMessage extends Message {
    protected ScMessage() {
        super();
    }
}