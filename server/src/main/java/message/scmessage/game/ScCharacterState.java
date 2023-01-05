package message.scmessage.game;

import message.csmessage.game.CsCharacterState;
import message.scmessage.ScMessage;

public class ScCharacterState extends ScMessage {
    public String id;
    public float locationX;
    public float locationY;
    public float directionX;
    public float directionY;
    public int state;

    public ScCharacterState(CsCharacterState csCharacterState) {
        this.id = csCharacterState.id;
        this.locationX = csCharacterState.locationX;
        this.locationY = csCharacterState.locationY;
        this.directionX = csCharacterState.directionX;
        this.directionY = csCharacterState.directionY;
        this.state = csCharacterState.state;
    }
}
