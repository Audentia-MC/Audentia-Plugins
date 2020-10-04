package fr.audentia.core.infrastructure.npc;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.NpcRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;

public class TOMLNpcRepository implements NpcRepository {

    private final String filePath;

    public TOMLNpcRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Npc> getNpc(String npcName) {

        FileConfig fileConfig = loadFile();

        if (fileConfig.isNull("npcs." + npcName)) {
            return Optional.empty();
        }

        Optional<Npc> build = Optional.of(aNpc()
                .withName(npcName)
                .withX(fileConfig.get("npcs." + npcName + ".x"))
                .withY(fileConfig.get("npcs." + npcName + ".y"))
                .withZ(fileConfig.get("npcs." + npcName + ".z"))
                .withYaw(fileConfig.get("npcs." + npcName + ".yaw"))
                .withPitch(fileConfig.get("npcs." + npcName + ".pitch"))
                .build());

        fileConfig.close();

        return build;
    }

    @Override
    public List<Npc> getAllNpc() {

        FileConfig fileConfig = loadFile();

        String[] names = fileConfig.getOrElse("names", new String[]{});

        List<Npc> npcs = Arrays.stream(names)
                .map(name -> aNpc()
                        .withName(name)
                        .withX(fileConfig.get("npcs." + name + ".x"))
                        .withY(fileConfig.get("npcs." + name + ".y"))
                        .withZ(fileConfig.get("npcs." + name + ".z"))
                        .withYaw(fileConfig.get("npcs." + name + ".yaw"))
                        .withPitch(fileConfig.get("npcs." + name + ".pitch"))
                        .build())
                .collect(Collectors.toList());

        fileConfig.close();
        return npcs;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = CommentedFileConfig.builder(filePath)
                .defaultResource("npcs.toml")
                .autosave()
                .build();

        fileConfig.load();
        return fileConfig;
    }

}
