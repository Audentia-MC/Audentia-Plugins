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
    public Location getPortalLocationInOverworld() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("nether.overworld_x")) {
            return null;
        }

        Location location = new Location(
                fileConfig.get("nether.overworld_x"),
                fileConfig.get("nether.overworld_y"),
                fileConfig.get("nether.overworld_z")
        );

        fileConfig.close();

        return location;
    }

    @Override
    public Location getPortalLocationInNether() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("nether.nether_x")) {
            return null;
        }

        Location location = new Location(
                fileConfig.get("nether.nether_x"),
                fileConfig.get("nether.nether_y"),
                fileConfig.get("nether.nether_z")
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
