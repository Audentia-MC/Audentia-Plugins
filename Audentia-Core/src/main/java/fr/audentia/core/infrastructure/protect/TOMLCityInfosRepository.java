package fr.audentia.core.infrastructure.protect;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.location.Location;
import fr.audentia.core.domain.protect.CityInfosRepository;

import java.io.File;

public class TOMLCityInfosRepository implements CityInfosRepository {

    private final String filePath;

    public TOMLCityInfosRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Location getCityLocation() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("city")) {
            return null;
        }

        Location location = new Location(
                fileConfig.get("city.x"),
                fileConfig.get("city.y"),
                fileConfig.get("city.z")
        );

        fileConfig.close();

        return location;
    }

    @Override
    public int getCityRadius() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("city")) {
            return 0;
        }

        return fileConfig.get("city.radius");
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();
        return fileConfig;
    }

}
