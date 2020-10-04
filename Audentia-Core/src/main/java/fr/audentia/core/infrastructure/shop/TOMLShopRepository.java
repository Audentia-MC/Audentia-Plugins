package fr.audentia.core.infrastructure.shop;

import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.shop.ShopRepository;

public class TOMLShopRepository implements ShopRepository {

    private final String filePath;

    public TOMLShopRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Shop getByNpcName(String npcName) {

        // TODO: get shops
        return null;
    }

}
