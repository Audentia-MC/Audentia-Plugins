package fr.audentia.core.domain.npc.spawn;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.Optional;
import java.util.stream.Collectors;

public class NpcSpawn {

    private final NpcSpawner npcSpawner;
    private final NpcRepository npcRepository;
    private final WorldNpcFinder worldNpcFinder;

    public NpcSpawn(NpcSpawner npcSpawner, NpcRepository npcRepository, WorldNpcFinder worldNpcFinder) {
        this.npcSpawner = npcSpawner;
        this.npcRepository = npcRepository;
        this.worldNpcFinder = worldNpcFinder;
    }

    public String spawnNpc(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);
        String result = "<error>Le PNJ " + npcName + " n'a pas été trouvé.";

        if (optionalNpc.isPresent()) {
            this.npcSpawner.spawnNpc(optionalNpc.get());
            result =  "<success>Le PNJ " + npcName + " a bien spawn.";
        }

        return result;
    }

    public String deleteNpc(String npcName) {

        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName);
        String result = "<error>Le PNJ " + npcName + " n'a pas été trouvé.";

        if (optionalNpc.isPresent()) {
            this.npcSpawner.deleteNpc(optionalNpc.get());
            result = "<success>Le PNJ " + npcName + " a bien été supprimé.";
        }

        return result;
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
