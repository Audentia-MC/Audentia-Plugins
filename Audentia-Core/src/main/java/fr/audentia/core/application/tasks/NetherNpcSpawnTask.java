package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.npc.NetherNcpSpawn;
import org.bukkit.scheduler.BukkitRunnable;

public class NetherNpcSpawnTask extends BukkitRunnable {

    private final NetherNcpSpawn netherNcpSpawn;

    public NetherNpcSpawnTask(NetherNcpSpawn netherNcpSpawn) {
        this.netherNcpSpawn = netherNcpSpawn;
    }

    @Override
    public void run() {

        netherNcpSpawn.doNpcAction();
    }

}
