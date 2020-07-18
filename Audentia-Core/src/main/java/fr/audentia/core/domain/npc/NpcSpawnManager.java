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

    public void spawnNpc(String npcName) {
        spawnNpcWithResult(npcName);
    }

    public void deleteNpc(String npcName) {
        deleteNpcWithResult(npcName);
    }

    public String spawnNpcWithResult(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.spawnNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien spawn.";
    }

    public String deleteNpcWithResult(String npcName) {

        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.deleteNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien été supprimé.";
    }

    public void spawnAllNpcs() {

        this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::spawnNpc);
    }

    public void deleteAllNpcs() {

        this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::deleteNpc);
    }

    public String spawnAllNpcsWithResult() {

        return this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::spawnNpcWithResult)
                .collect(Collectors.joining("\n"));
    }

    public String deleteAllNpcsWithResult() {

        return this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::deleteNpcWithResult)
                .collect(Collectors.joining("\n"));
    }

    public void reloadNpc(String npcName) {

        deleteNpc(npcName);
        spawnNpc(npcName);
    }

    public void reloadAllNpcs() {

        this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::deleteNpc);

        this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::spawnNpc);
    }

    public String reloadNpcWithResult(String npcName) {

        String result = "<success>Le PNJ " + npcName + " a bien été rechargé.";

        if (deleteNpcWithResult(npcName).contains("<error>") || spawnNpcWithResult(npcName).contains("<error>")) {
            result = "<error>Une erreur s'est produite.";
        }

        return result;
    }

    public String reloadAllNpcsWithResult() {

        reloadAllNpcs();
        return "<success>Les PNJ ont bien été rechargés.";
    }

}
