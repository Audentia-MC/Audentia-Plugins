package fr.audentia.core.infrastructure.damage;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.damage.ColiseumLocationRepository;
import fr.audentia.core.domain.model.location.Location;

import java.io.File;

public class TOMLColiseumLocationRepository implements ColiseumLocationRepository {

    private final String filePath;

    public TOMLColiseumLocationRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Location getColiseumLocation() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("coliseum.x")) {
            return null;
        }

        Location location = new Location(
                fileConfig.get("coliseum.x"),
                fileConfig.get("coliseum.y"),
                fileConfig.get("coliseum.z")
        );

        fileConfig.close();

        return location;
    }

    @Override
    public int getColiseumSize() {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("coliseum.x")) {
            return 0;
        }

        return fileConfig.get("coliseum.size");
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();
        return fileConfig;
    }

}
