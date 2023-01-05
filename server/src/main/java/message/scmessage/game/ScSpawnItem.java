package message.scmessage.game;

import entity.game.item.Item;
import message.scmessage.ScMessage;

public class ScSpawnItem extends ScMessage {
    public String itemID;
    public String itemName;
    public int positionX;
    public int positionY;

    public ScSpawnItem(Item item, int initX, int initY) {
        this.itemID = item.getItemID();
        this.itemName = item.getItemName();
        this.positionX = initX;
        this.positionY = initY;
    }
}
