package fr.audentia.core.domain.npc.bank;

import fr.audentia.core.domain.npc.spawn.NpcSpawnManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankNpcSpawnerTest {

    private BankNpcProvider bankNpcProvider;
    private NpcSpawnManager npcSpawnManager;
    private BankNpcSpawner bankNpcSpawner;

    @Before
    public void setUp() {
        this.bankNpcProvider = Mockito.mock(BankNpcProvider.class);
        this.npcSpawnManager = Mockito.mock(NpcSpawnManager.class);
        this.bankNpcSpawner = new BankNpcSpawner(bankNpcProvider, npcSpawnManager);
    }

    @Test
    public void spawnBanNpc_shouldSpawnNpc_whenNpcExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        String result = bankNpcSpawner.spawnBankNpc();

        assertThat(result).isEqualTo("<success>Le PNJ de la banque a bien spawn.");
        verify(npcSpawnManager, times(1)).spawnNpc("Tony");
    }

    @Test
    public void spawnBanNpc_shouldDoNothing_whenNpcDoesNotExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.empty());

        String result = bankNpcSpawner.spawnBankNpc();

        assertThat(result).isEqualTo("<error>Le PNJ de la banque n'a pas été trouvé.");
        verifyNoInteractions(npcSpawnManager);
    }

}
