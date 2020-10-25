package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.Optional;

public class NetherNcpSpawn {

    private final NpcSpawner npcSpawner;
    private final NetherNpcRepository netherNpcRepository;
    private final WorldNpcFinder worldNpcFinder;

    public NetherNcpSpawn(NpcSpawner npcSpawner, NetherNpcRepository netherNpcRepository, WorldNpcFinder worldNpcFinder) {
        this.npcSpawner = npcSpawner;
        this.netherNpcRepository = netherNpcRepository;
        this.worldNpcFinder = worldNpcFinder;
    }

    public void spawnNetherNpc() {

        Optional<Npc> optionalNpc = this.netherNpcRepository.getRandomNetherNpcLocation();
        optionalNpc.ifPresent(npc -> this.npcSpawner.spawnNpc(npc, "world_nether"));
    }

    public void deleteNetherNpc() {

        String npcName = netherNpcRepository.getNetherNpcName();
        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName, "world_nether");

        optionalNpc.ifPresent(npc -> this.npcSpawner.deleteNpc(npc, "world_nether"));
    }

}
