package fr.audentia.core.application.commands.tu;

import fr.audentia.core.application.commands.CommandBalance;
import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.players.utils.ColorsUtils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandBalanceTest {

    @Mock
    private Player player;

    @Mock
    private ConsoleCommandSender consoleCommandSender;

    @Mock
    private BalanceManage balanceManage;

    private CommandBalance commandBalance;

    @BeforeEach
    void setUp() {
        commandBalance = new CommandBalance(balanceManage);
    }

    @Test
    @DisplayName("balance should return the balance when people is a player")
    void balance_shouldReturnBalance_whenPeopleIsPlayer() {

        when(balanceManage.getBalanceWithMessage(any())).thenReturn("");

        boolean result = commandBalance.onCommand(player, null, null, null);

        verify(player, times(1)).getUniqueId();
        verify(player, times(1)).sendMessage(anyString());
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("balance should do nothing when people isn't a player")
    void balance_shouldDoNothing_whenPeopleIsNotAPlayer() {

        boolean result = commandBalance.onCommand(consoleCommandSender, null, null, null);

        verifyNoInteractions(consoleCommandSender);
        verifyNoInteractions(balanceManage);
        assertThat(result).isEqualTo(true);
    }

}
