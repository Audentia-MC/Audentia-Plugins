package fr.audentia.core.infrastructure.npc;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.NetherNpcRepository;

import java.util.Optional;
import java.util.Random;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;

public class TOMLNetherNpcRepository implements NetherNpcRepository {

    private final String filePath;

    public TOMLNetherNpcRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Npc> getRandomNetherNpcLocation() {

        FileConfig fileConfig = loadFile();

        String name = fileConfig.get("name");
        int locationsCount = fileConfig.get("locations_count");
        int start = new Random().nextInt(locationsCount);

        for (int i = start + 1; i != start; i++) {

            if (i >= locationsCount) {
                i = 0;
            }

            if (fileConfig.isNull(String.valueOf(i))) {
                continue;
            }

            Optional<Npc> build = Optional.of(aNpc()
                    .withName(name)
                    .withX(fileConfig.get("locations." + i + ".x"))
                    .withY(fileConfig.get("locations." + i + ".y"))
                    .withZ(fileConfig.get("locations." + i + ".z"))
                    .withYaw(fileConfig.get("locations." + i + ".yaw"))
                    .withPitch(fileConfig.get("locations." + i + ".pitch"))
                    .build());

            fileConfig.close();

            return build;
        }

        fileConfig.close();
        return Optional.empty();
    }

    @Override
    public String getNetherNpcName() {
        FileConfig fileConfig = loadFile();

        String name = fileConfig.get("name");
        fileConfig.close();
        return name;
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
