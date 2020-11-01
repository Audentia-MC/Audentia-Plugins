package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.npc.NetherNpcSpawn;
import org.bukkit.scheduler.BukkitRunnable;

public class NetherNpcSpawnTask extends BukkitRunnable {

    private final NetherNpcSpawn netherNpcSpawn;

    public NetherNpcSpawnTask(NetherNpcSpawn netherNpcSpawn) {
        this.netherNpcSpawn = netherNpcSpawn;
    }

    @Override
    public void run() {

        netherNpcSpawn.doNpcAction();
    }

}
