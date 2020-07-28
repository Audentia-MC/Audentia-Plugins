package fr.audentia.core.application.commands.tu;

import fr.audentia.core.application.commands.CommandAbout;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandsAboutTest {

    @Mock
    private Player player;

    private CommandAbout commandAbout;

    @BeforeEach
    void setUp() {
        commandAbout = new CommandAbout("1");
    }

    @Test
    @DisplayName("about should send message to player")
    void about_shouldSendAboutMessageToPlayer() {

        boolean result = commandAbout.onCommand(player, null, null, null);

        verify(player, times(8)).sendMessage(anyString());
        assertThat(result).isEqualTo(true);
    }

}
