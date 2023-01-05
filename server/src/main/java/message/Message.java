package message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Message {
    public long generatedTime;

    protected Message() {
        generatedTime = System.currentTimeMillis();
    }

    public String messageType() {
        return this.getClass().getSimpleName();
    }
}