package fr.audentia.core.infrastructure.shop;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.model.shop.ShopItem;
import fr.audentia.core.domain.shop.ShopRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TOMLShopRepository implements ShopRepository {

    private final String filePath;

    public TOMLShopRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Shop getByNpcName(String npcName) {

        FileConfig fileConfig = loadFile();

        List<ShopItem> items = fileConfig.getOrElse(npcName + ".items", new ArrayList<String>())
                .stream()
                .map(material -> new ShopItem(material, fileConfig.get(npcName + "." + material)))
                .collect(Collectors.toList());

        Shop shop = new Shop(npcName, items);
        fileConfig.close();

        return shop;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "shops.toml");

        fileConfig.load();
        return fileConfig;
    }

}
