package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.npc.NpcSpawn;
import org.bukkit.scheduler.BukkitRunnable;

public class NpcRemoverTask extends BukkitRunnable {

    private final NpcSpawn npcSpawn;

    public NpcRemoverTask(NpcSpawn npcSpawn) {
        this.npcSpawn = npcSpawn;
    }

    @Override
    public void run() {

        npcSpawn.deleteAllNpcs();

    }

}
