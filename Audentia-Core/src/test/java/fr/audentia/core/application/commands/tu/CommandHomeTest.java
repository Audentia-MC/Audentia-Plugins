package fr.audentia.core.application.commands.tu;

import fr.audentia.core.application.commands.CommandHome;
import fr.audentia.core.domain.home.HomeManage;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandHomeTest {

    @Mock
    private Player player;

    @Mock
    private ConsoleCommandSender consoleCommandSender;

    @Mock
    private HomeManage homeManage;

    private CommandHome commandHome;

    @BeforeEach
    void setUp() {
        commandHome = new CommandHome(homeManage);
    }

    @Test
    @DisplayName("home should call home manage to tp top home number 1 and send message when there isn't any argument")
    void home_shouldCallHomeManageToTpToHome1_whenThereIsNotAnyArgument() {

        when(homeManage.teleportToHome(any(), eq(1))).thenReturn("");

        boolean result = commandHome.onCommand(player, null, null, new String[0]);

        verify(homeManage, times(1)).teleportToHome(any(), eq(1));
        verify(player, times(1)).sendMessage(anyString());
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("home should call home manage to tp top home number 2 and send message when there is an argument")
    void home_shouldCallHomeManageToTpToHome2_whenThereIsArgument2() {

        when(homeManage.teleportToHome(any(), eq(2))).thenReturn("");

        boolean result = commandHome.onCommand(player, null, null, new String[]{"2"});

        verify(homeManage, times(1)).teleportToHome(any(), eq(2));
        verify(player, times(1)).sendMessage(anyString());
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("balance should do nothing when people isn't a player")
    void balance_shouldDoNothing_whenPeopleIsNotAPlayer() {

        boolean result = commandHome.onCommand(consoleCommandSender, null, null, null);

        verifyNoInteractions(consoleCommandSender);
        verifyNoInteractions(homeManage);
        assertThat(result).isEqualTo(true);
    }

}
