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
            result = npcSpawn.spawnNpc(name.get());
        }

        return result;
    }

    public String deleteBankNpc() {

        Optional<String> name = bankNpcProvider.getName();
        String result = "<error>Le PNJ de la banque n'a pas été trouvé.";

        if (name.isPresent()) {
            result = npcSpawn.deleteNpc(name.get());
        }

        return result;
    }

    public String reloadBankNpc() {

        Optional<String> name = bankNpcProvider.getName();
        String result = "<error>Le PNJ de la banque n'a pas été trouvé.";

        if (name.isPresent()) {
            result = npcSpawn.forceReloadNpc(name.get());
        }

        return result;
    }

}
