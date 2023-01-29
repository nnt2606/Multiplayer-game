package entity.game.item;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Item {
    protected String itemID;
    protected String itemName;
    protected int addPoint;

    protected Item() {
        itemID = UUID.randomUUID().toString();
    }
}
