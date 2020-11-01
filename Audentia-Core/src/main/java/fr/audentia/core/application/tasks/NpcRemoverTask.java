package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.bank.BankNpcSpawn;
import fr.audentia.core.domain.npc.NetherNpcSpawn;
import fr.audentia.core.domain.npc.NpcSpawn;

public class NpcRemoverTask {

    private final NpcSpawn npcSpawn;
    private final NetherNpcSpawn netherNpcSpawn;
    private final BankNpcSpawn bankNpcSpawn;

    public NpcRemoverTask(NpcSpawn npcSpawn, NetherNpcSpawn netherNpcSpawn, BankNpcSpawn bankNpcSpawn) {
        this.npcSpawn = npcSpawn;
        this.netherNpcSpawn = netherNpcSpawn;
        this.bankNpcSpawn = bankNpcSpawn;
    }

    public void run() {

        npcSpawn.deleteAllNpcs();
        netherNpcSpawn.deleteNetherNpc();
        bankNpcSpawn.deleteBankNpc();
    }

}
