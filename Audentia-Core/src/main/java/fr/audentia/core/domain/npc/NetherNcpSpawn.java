package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.time.LocalTime;
import java.util.Optional;

public class NetherNcpSpawn {

    private final NpcSpawner npcSpawner;
    private final NetherNpcRepository netherNpcRepository;
    private final WorldNpcFinder worldNpcFinder;
    private final NetherTimesRepository netherTimesRepository;
    private boolean spawned = true;

    public NetherNcpSpawn(NpcSpawner npcSpawner, NetherNpcRepository netherNpcRepository, WorldNpcFinder worldNpcFinder, NetherTimesRepository netherTimesRepository) {
        this.npcSpawner = npcSpawner;
        this.netherNpcRepository = netherNpcRepository;
        this.worldNpcFinder = worldNpcFinder;
        this.netherTimesRepository = netherTimesRepository;
    }

    public void doNpcAction() {

        LocalTime actualTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(netherTimesRepository.getStartHour(), netherTimesRepository.getStartMinute());
        LocalTime endTime = LocalTime.of(netherTimesRepository.getEndHour(), netherTimesRepository.getEndMinute());

        if (!spawned && actualTime.isAfter(startTime) && actualTime.isBefore(endTime)) {
            spawnNetherNpc();
            spawned = true;
        }

        if (spawned && !(actualTime.isAfter(startTime) && actualTime.isBefore(endTime))) {
            deleteNetherNpc();
            spawned = false;
        }

    }

    private void spawnNetherNpc() {

        Optional<Npc> optionalNpc = this.netherNpcRepository.getRandomNetherNpcLocation();
        optionalNpc.ifPresent(npc -> this.npcSpawner.spawnNpc(npc, "world_nether"));
    }

    private void deleteNetherNpc() {

        String npcName = netherNpcRepository.getNetherNpcName();
        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName, "world_nether");

        optionalNpc.ifPresent(npc -> this.npcSpawner.deleteNpc(npc, "world_nether"));
    }

}
