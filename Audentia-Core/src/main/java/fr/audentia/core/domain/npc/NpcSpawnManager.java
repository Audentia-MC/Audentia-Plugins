package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.Optional;
import java.util.stream.Collectors;

public class NpcSpawnManager {

    private final NpcSpawner npcSpawner;
    private final NpcRepository npcRepository;
    private final WorldNpcFinder worldNpcFinder;

    public NpcSpawnManager(NpcSpawner npcSpawner, NpcRepository npcRepository, WorldNpcFinder worldNpcFinder) {
        this.npcSpawner = npcSpawner;
        this.npcRepository = npcRepository;
        this.worldNpcFinder = worldNpcFinder;
    }

    public String spawnNpc(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.spawnNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien spawn.";
    }

    public String deleteNpc(String npcName) {

        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.deleteNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien été supprimé.";
    }

    public String spawnAllNpcs() {

        return this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::spawnNpc)
                .collect(Collectors.joining("\n"));
    }

    public String deleteAllNpcs() {

        return this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::deleteNpc)
                .collect(Collectors.joining("\n"));
    }

    public String reloadNpc(String npcName) {

        String result = "<success>Le PNJ " + npcName + " a bien été rechargé.";

        if (deleteNpc(npcName).contains("<error>") || spawnNpc(npcName).contains("<error>")) {
            result = "<error>Une erreur s'est produite.";
        }

        return result;
    }

    public String reloadAllNpcs() {

        this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::deleteNpc);

        this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::spawnNpc);

        return "<success>Les PNJ ont bien été rechargés.";
    }

}
