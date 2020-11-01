package fr.audentia.core.infrastructure.border;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.border.BorderInfosRepository;
import fr.audentia.core.domain.model.border.BorderLocation;
import fr.audentia.core.domain.model.border.BorderSize;

import java.io.File;

public class TOMLBorderInfosRepository implements BorderInfosRepository {

    private final String filePath;

    public TOMLBorderInfosRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public BorderSize getBorderSize() {

        FileConfig fileConfig = loadFileConfig();

        int borderSize = fileConfig.getOptionalInt("border.size").orElse(0);

        fileConfig.close();

        return new BorderSize(borderSize);
    }

    @Override
    public BorderLocation getBorderLocation() {

        FileConfig fileConfig = loadFileConfig();

        int x = fileConfig.getOptionalInt("border.x").orElse(0);
        int z = fileConfig.getOptionalInt("border.z").orElse(0);

        fileConfig.close();

        return new BorderLocation(x, z);
    }

    private FileConfig loadFileConfig() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();
        return fileConfig;
    }

}
