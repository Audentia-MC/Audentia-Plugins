package fr.audentia.core.infrastructure.shop;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.model.shop.ShopItem;
import fr.audentia.core.domain.shop.ShopRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TOMLNetherShopRepository implements ShopRepository {

    private final String filePath;

    public TOMLNetherShopRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Shop getByNpcName(String npcName) {

        FileConfig fileConfig = loadFile();

        List<ShopItem> items = fileConfig.getOrElse("shop.items", new ArrayList<String>())
                .stream()
                .map(material -> new ShopItem(material, fileConfig.getOrElse("shop." + material, 0.0)))
                .collect(Collectors.toList());

        Shop shop = new Shop(npcName, items);
        fileConfig.close();

        return shop;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "nether.toml");

        fileConfig.load();
        return fileConfig;
    }

}
