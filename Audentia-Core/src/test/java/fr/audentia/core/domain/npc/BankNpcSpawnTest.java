package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankNpcProvider;
import fr.audentia.core.domain.bank.BankNpcSpawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankNpcSpawnTest {

    @Mock
    private BankNpcProvider bankNpcProvider;

    @Mock
    private NpcSpawn npcSpawn;

    private BankNpcSpawn bankNpcSpawn;

    @BeforeEach
    void setUp() {
        this.bankNpcSpawn = new BankNpcSpawn(bankNpcProvider, npcSpawn);
    }

    @Test
    @DisplayName("spawnBankNpc should spawn the npc and return success message when the npc's informations are stored")
    void spawnBanNpc_shouldSpawnNpc_whenNpcExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        String result = bankNpcSpawn.spawnBankNpc();

        assertThat(result).isEqualTo("<success>Le PNJ de la banque a bien spawn.");
        verify(npcSpawn, times(1)).spawnNpc("Tony");
    }

    @Test
    @DisplayName("spawnBankNpc shouldn't spawn the npc and return error message when the npc's informations aren't stored")
    void spawnBanNpc_shouldDoNothing_whenNpcDoesNotExists() {

        when(bankNpcProvider.getName()).thenReturn(Optional.empty());

        String result = bankNpcSpawn.spawnBankNpc();

        assertThat(result).isEqualTo("<error>Le PNJ de la banque n'a pas été trouvé.");
        verifyNoInteractions(npcSpawn);
    }

}
