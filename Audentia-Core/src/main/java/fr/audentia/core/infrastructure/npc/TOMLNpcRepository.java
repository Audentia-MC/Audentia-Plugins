package fr.audentia.core.infrastructure.npc;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.NpcRepository;

import java.io.File;
import java.util.*;
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

        if (!fileConfig.contains(npcName)) {
            return Optional.empty();
        }

        Optional<Npc> build = Optional.of(aNpc()
                .withName(npcName)
                .withX(fileConfig.get(npcName + ".x"))
                .withY(fileConfig.get(npcName + ".y"))
                .withZ(fileConfig.get(npcName + ".z"))
                .withYaw(Float.parseFloat(String.valueOf(fileConfig.getOrElse(npcName + ".yaw", 0.0d))))
                .withPitch(Float.parseFloat(String.valueOf(fileConfig.getOrElse(npcName + ".pitch", 0.0d))))
                .build());

        fileConfig.close();

        return build;
    }

    @Override
    public List<Npc> getAllNpc() {

        FileConfig fileConfig = loadFile();

        List<Npc> npcs = fileConfig.getOrElse("names", new ArrayList<String>()).stream()
                .map(npcName -> aNpc()
                            .withName(npcName)
                            .withX(fileConfig.get(npcName + ".x"))
                            .withY(fileConfig.get(npcName + ".y"))
                            .withZ(fileConfig.get(npcName + ".z"))
                            .withYaw(Float.parseFloat(String.valueOf(fileConfig.getOrElse(npcName + ".yaw", 0.0d))))
                            .withPitch(Float.parseFloat(String.valueOf(fileConfig.getOrElse(npcName + ".pitch", 0.0d))))
                            .build())
                .collect(Collectors.toList());

        fileConfig.close();
        return npcs;
    }

    private FileConfig loadFile() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "npcs.toml");

        fileConfig.load();
        return fileConfig;
    }

}
