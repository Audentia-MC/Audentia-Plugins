package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.npc.NpcSpawn;

import java.util.Optional;

public class BankNpcSpawn {

    private final BankNpcProvider bankNpcProvider;
    private final NpcSpawn npcSpawn;

    public BankNpcSpawn(BankNpcProvider bankNpcProvider, NpcSpawn npcSpawn) {
        this.bankNpcProvider = bankNpcProvider;
        this.npcSpawn = npcSpawn;
    }

    public String spawnBankNpc() {

        Optional<String> name = bankNpcProvider.getName();
        String result = "<error>Le PNJ de la banque n'a pas été trouvé.";

        if (name.isPresent()) {
            npcSpawn.spawnNpc(name.get());
            result = "<success>Le PNJ de la banque a bien spawn.";
        }

        return result;
    }

}
