package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.List;
import java.util.Optional;

public class NpcSpawnManager {

    private final NpcSpawner npcSpawner;
    private final NpcRepository npcRepository;

    public NpcSpawnManager(NpcSpawner npcSpawner, NpcRepository npcRepository) {
        this.npcSpawner = npcSpawner;
        this.npcRepository = npcRepository;
    }

    public void spawnNpc(String npcName) {
        spawnNpcWithResult(npcName);
    }

    public String spawnNpcWithResult(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.spawnNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien spawn.";
    }

    public void deleteNpc(String npcName) {
        deleteNpcWithResult(npcName);
    }

    public String deleteNpcWithResult(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);

        if (!optionalNpc.isPresent()) {
            return "<error>Le PNJ " + npcName + " n'a pas été trouvé.";
        }

        this.npcSpawner.deleteNpcNpc(optionalNpc.get());

        return "<success>Le PNJ " + npcName + " a bien été supprimé.";
    }

    public void spawnAllNpc() {

        List<Npc> npcs = this.npcRepository.getAllNpc();

        npcs.stream()
                .map(npc -> npc.name)
                .forEach(this::spawnNpc);

    }

}
