package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.NpcLocation;

import java.util.Optional;

public class NpcManager {

    private final NpcSpawner npcSpawner;
    private final NpcRepository npcRepository;

    public NpcManager(NpcSpawner npcSpawner, NpcRepository npcRepository) {
        this.npcSpawner = npcSpawner;
        this.npcRepository = npcRepository;
    }

    public void spawnPnj(String npcName) {

        Optional<NpcLocation> optionalNpcLocation = this.npcRepository.getPnjLocation(npcName);

        if (!optionalNpcLocation.isPresent()) {
            return;
        }

        this.npcSpawner.spawnNpc(optionalNpcLocation.get());
    }

}
