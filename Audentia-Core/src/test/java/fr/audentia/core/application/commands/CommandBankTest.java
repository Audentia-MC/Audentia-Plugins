package fr.audentia.core.application.commands;

import fr.audentia.core.domain.bank.BankSlotsProvide;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandBankTest {

    @Mock
    private Player player;

    @Mock
    private BankSlotsProvide bankSlotsProvide;

    private CommandBank commandBank;

    @BeforeEach
    void setUp() {
        commandBank = new CommandBank(bankSlotsProvide);
    }

    @Test
    @DisplayName("bank should display banks open slots")
    void bank_shouldDisplayBanksOpenSlots() {

        when(bankSlotsProvide.getBankSlots()).thenReturn("");

        boolean result = commandBank.onCommand(player, null, null, null);

        verify(bankSlotsProvide, times(1)).getBankSlots();
        verify(player, times(1)).sendMessage(anyString());
        assertThat(result).isEqualTo(true);
    }

}
