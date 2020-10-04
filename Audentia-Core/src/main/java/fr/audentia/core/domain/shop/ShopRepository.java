package fr.audentia.core.domain.shop;

import fr.audentia.core.domain.model.shop.Shop;

public interface ShopRepository {

    Shop getByNpcName(String npcName);

}
