package fr.audentia.core.domain.npc.bank;

import fr.audentia.core.domain.npc.spawn.NpcSpawnManager;

import java.util.Optional;

public class BankNpcSpawner {

    private final BankNpcProvider bankNpcProvider;
    private NpcSpawnManager npcSpawnManager;

    public BankNpcSpawner(BankNpcProvider bankNpcProvider, NpcSpawnManager npcSpawnManager) {
        this.bankNpcProvider = bankNpcProvider;
        this.npcSpawnManager = npcSpawnManager;
    }

    public String spawnBankNpc() {

        Optional<String> name = bankNpcProvider.getName();
        String result = "<error>Le PNJ de la banque n'a pas été trouvé.";

        if (name.isPresent()) {
            npcSpawnManager.spawnNpc(name.get());
            result = "<success>Le PNJ de la banque a bien spawn.";
        }

        return result;
    }

}
