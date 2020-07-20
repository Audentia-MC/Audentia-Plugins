package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankNpcProvider;
import fr.audentia.core.domain.bank.BankNpcSpawn;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankNpcSpawnTest {

    private BankNpcProvider bankNpcProvider;
    private NpcSpawn npcSpawn;
    private BankNpcSpawn bankNpcSpawn;

    @Before
    public void setUp() {
        this.bankNpcProvider = Mockito.mock(BankNpcProvider.class);
        this.npcSpawn = Mockito.mock(NpcSpawn.class);
        this.bankNpcSpawn = new BankNpcSpawn(bankNpcProvider, npcSpawn);
    }

    @Test
    public void spawnBanNpc_shouldSpawnNpc_whenNpcExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        String result = bankNpcSpawn.spawnBankNpc();

        assertThat(result).isEqualTo("<success>Le PNJ de la banque a bien spawn.");
        verify(npcSpawn, times(1)).spawnNpc("Tony");
    }

    @Test
    public void spawnBanNpc_shouldDoNothing_whenNpcDoesNotExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.empty());

        String result = bankNpcSpawn.spawnBankNpc();

        assertThat(result).isEqualTo("<error>Le PNJ de la banque n'a pas été trouvé.");
        verifyNoInteractions(npcSpawn);
    }

}
