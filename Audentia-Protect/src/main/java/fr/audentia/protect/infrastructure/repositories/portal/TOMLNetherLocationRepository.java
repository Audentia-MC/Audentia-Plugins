package fr.audentia.protect.infrastructure.repositories.portal;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.portals.NetherLocationRepository;

import java.io.File;

public class TOMLNetherLocationRepository implements NetherLocationRepository {

    private final String filePath;

    public TOMLNetherLocationRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Location getPortalLocation() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("nether.x")) {
            return null;
        }

        Location location = new Location(
                fileConfig.get("nether.x"),
                fileConfig.get("nether.y"),
                fileConfig.get("nether.z")
        );

        fileConfig.close();

        return location;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();
        return fileConfig;
    }

}
