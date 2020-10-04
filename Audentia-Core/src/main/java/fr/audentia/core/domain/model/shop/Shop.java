package fr.audentia.core.domain.model.shop;

import java.util.List;

public class Shop {

    public final String npcName;
    public final List<ShopItem> items;

    public Shop(String npcName, List<ShopItem> items) {
        this.npcName = npcName;
        this.items = items;
    }

}
