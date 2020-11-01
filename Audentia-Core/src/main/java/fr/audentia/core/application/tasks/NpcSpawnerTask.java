package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.bank.BankNpcSpawn;
import fr.audentia.core.domain.npc.NpcSpawn;
import org.bukkit.scheduler.BukkitRunnable;

public class NpcSpawnerTask extends BukkitRunnable {

    private final NpcSpawn npcSpawn;
    private final BankNpcSpawn bankNpcSpawn;

    public NpcSpawnerTask(NpcSpawn npcSpawn, BankNpcSpawn bankNpcSpawn) {
        this.npcSpawn = npcSpawn;
        this.bankNpcSpawn = bankNpcSpawn;
    }

    @Override
    public void run() {

        npcSpawn.spawnAllNpcs();
        bankNpcSpawn.spawnBankNpc();
    }

}
