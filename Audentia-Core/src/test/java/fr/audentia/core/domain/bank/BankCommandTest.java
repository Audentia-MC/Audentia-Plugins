package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankCommandTest {

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private BankSlotsRepository bankSlotsRepository;

    private BankSlotsProvide bankSlotsProvider;

    @BeforeEach
    void setUp() {
        bankSlotsProvider = new BankSlotsProvide(gamesInfosRepository, bankSlotsRepository);
    }

    @Test
    @DisplayName("execute should display bank open slots")
    void execute_shouldReturnBankOpenSlots() {

        List<Slot> slots = Arrays.asList(new Slot(2, 4), new Slot(10, 12), new Slot(18, 20));
        BankSlots bankSlots = new BankSlots(slots);
        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(bankSlots);

        String result = bankSlotsProvider.getBankSlots();

        assertThat(result).isEqualTo("#FCB91FLes horaires d'ouverture de la banque pour aujourd'hui sont : 2h-4h, 10h-12h, 18h-20h.");
    }

}
