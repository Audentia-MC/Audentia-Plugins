package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.npc.NetherNcpSpawn;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;

public class NetherNpcSpawnTask extends BukkitRunnable {

    private final NetherNcpSpawn netherNcpSpawn;
    private boolean spawned = false;

    public NetherNpcSpawnTask(NetherNcpSpawn netherNcpSpawn) {
        this.netherNcpSpawn = netherNcpSpawn;
    }

    @Override
    public void run() {

        LocalTime localTime = LocalTime.now();

        if (localTime.getHour() == 17 && localTime.getMinute() == 30 && !spawned) {
            netherNcpSpawn.spawnNetherNpc();
            spawned = true;
        }

        if (localTime.getHour() == 19 && localTime.getMinute() == 30 && spawned) {
            netherNcpSpawn.deleteNetherNpc();
            spawned = false;
        }

    }

}
