package fr.audentia.core.infrastructure.damage;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.damage.TimeProtectionAtStartProvider;

import java.io.File;
import java.util.Optional;

public class TOMLTimeProtectionAtStartProvider implements TimeProtectionAtStartProvider {

    private final String filePath;

    public TOMLTimeProtectionAtStartProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int getMinutesOfProtection() {


        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        int timeProtection = fileConfig.get("game.time_protection");

        fileConfig.close();

        return timeProtection;
    }

}
