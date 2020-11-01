package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;

public class NetherNpcSpawn {

    public static final String NETHER_WORLD_NAME = "world_nether";

    private final NpcSpawner npcSpawner;
    private final NetherNpcRepository netherNpcRepository;
    private final WorldNpcFinder worldNpcFinder;
    private final NetherTimesRepository netherTimesRepository;

    public NetherNpcSpawn(NpcSpawner npcSpawner, NetherNpcRepository netherNpcRepository, WorldNpcFinder worldNpcFinder, NetherTimesRepository netherTimesRepository) {
        this.npcSpawner = npcSpawner;
        this.netherNpcRepository = netherNpcRepository;
        this.worldNpcFinder = worldNpcFinder;
        this.netherTimesRepository = netherTimesRepository;
    }

    public void doNpcAction() {

        LocalTime actualTime = ZonedDateTime.now().toLocalTime();
        LocalTime startTime = LocalTime.of(netherTimesRepository.getStartHour(), netherTimesRepository.getStartMinute());
        LocalTime endTime = LocalTime.of(netherTimesRepository.getEndHour(), netherTimesRepository.getEndMinute());

        boolean inRange = actualTime.isAfter(startTime) && actualTime.isBefore(endTime);

        String npcName = netherNpcRepository.getNetherNpcName();
        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName, "world_nether");

        if (inRange && !optionalNpc.isPresent()) {
            spawnNetherNpc();
        }

        if (!inRange && optionalNpc.isPresent()) {
            deleteNetherNpc();
        }

    }

    private void spawnNetherNpc() {

        this.netherNpcRepository.getRandomNetherNpcLocation()
                .ifPresent(npc -> this.npcSpawner.spawnNpc(npc, NETHER_WORLD_NAME));
    }

    public void deleteNetherNpc() {

        String npcName = netherNpcRepository.getNetherNpcName();
        this.worldNpcFinder.findNpc(npcName, NETHER_WORLD_NAME)
                .ifPresent(npc -> this.npcSpawner.deleteNpc(npc, NETHER_WORLD_NAME));
    }

}
