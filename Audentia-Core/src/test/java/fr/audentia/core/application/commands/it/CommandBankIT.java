package fr.audentia.core.application.commands.it;

import fr.audentia.core.application.commands.CommandBank;
import fr.audentia.core.domain.bank.BankSlotsProvide;
import fr.audentia.core.domain.bank.BankSlotsRepository;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.Slot;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandBankIT {

    @Mock
    private Player player;

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private BankSlotsRepository bankSlotsRepository;

    private CommandBank commandBank;

    @BeforeEach
    void setUp() {
        BankSlotsProvide bankSlotsProvide = new BankSlotsProvide(gamesInfosRepository, bankSlotsRepository);
        commandBank = new CommandBank(bankSlotsProvide);
    }

    @Test
    @DisplayName("bank should display banks open slots")
    void bank_shouldDisplayBanksOpenSlots() {

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Arrays.asList(new Slot(8, 10), new Slot(16, 18))));

        boolean result = commandBank.onCommand(player, null, null, null);

        verify(gamesInfosRepository, times(1)).getDay();
        verify(bankSlotsRepository, times(1)).getBankOpenSlots(any());
        verify(player, times(1)).sendMessage(ChatUtils.format("#FCB91FLes horaires d'ouverture de la banque pour aujourd'hui sont : 8h-10h, 16h-18h."));
        assertThat(result).isEqualTo(true);
    }

}

