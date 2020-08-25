package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.npc.NpcSpawn;
import org.bukkit.scheduler.BukkitRunnable;

public class NpcSpawnerTask extends BukkitRunnable {

    private final NpcSpawn npcSpawn;

    public NpcSpawnerTask(NpcSpawn npcSpawn) {
        this.npcSpawn = npcSpawn;
    }

    @Override
    public void run() {

        npcSpawn.spawnAllNpcs();

    }

}
