package message.scmessage.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.game.item.Item;
import message.scmessage.ScMessage;

public class ScUserObtainItem extends ScMessage {
    public int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String userID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String itemID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer addPoint;

    public ScUserObtainItem() {
        this.statusCode = 403;
        this.userID = null;
        this.itemID = null;
        this.addPoint = null;
    }

    public ScUserObtainItem(Item item, String userID) {
        this.statusCode = 200;
        this.userID = userID;
        this.itemID = item.getItemID();
        this.addPoint = item.getAddPoint();
    }
}
