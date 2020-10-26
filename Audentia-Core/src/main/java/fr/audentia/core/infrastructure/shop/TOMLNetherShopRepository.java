package fr.audentia.core.infrastructure.shop;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.model.shop.ShopItem;
import fr.audentia.core.domain.shop.ShopRepository;

import java.util.ArrayList;
import java.util.List;

public class TOMLNetherShopRepository implements ShopRepository {

    private final String filePath;

    public TOMLNetherShopRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Shop getByNpcName(String npcName) {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("shop")) {
            return null;
        }

        String[] itemsMaterial = fileConfig.getOrElse("shop.items", new String[]{});

        List<ShopItem> items = new ArrayList<>();

        for (String material : itemsMaterial) {

            items.add(new ShopItem(
                    material,
                    fileConfig.get("shop." + material)
            ));
        }

        Shop shop = new Shop(npcName, items);
        fileConfig.close();

        return shop;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = CommentedFileConfig.builder(filePath)
                .defaultResource("nether.toml")
                .autosave()
                .build();

        fileConfig.load();
        return fileConfig;
    }

}
