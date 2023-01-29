package entity.game.item.factory;

import entity.game.item.Apple;
import entity.game.item.Item;

public class ItemFactory {
    public static Item getItem(String itemType) {
        switch (itemType) {
            case "Apple":
                return new Apple();
        }
        return null;
    }
}
