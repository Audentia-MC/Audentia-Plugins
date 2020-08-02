package fr.audentia.core.application.commands.tu;

import fr.audentia.core.application.commands.CommandStaff;
import fr.audentia.core.domain.staff.StaffInventoryOpen;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandStaffTest {

    @Mock
    private Player player;

    @Mock
    private ConsoleCommandSender consoleCommandSender;

    @Mock
    private StaffInventoryOpen staffInventoryOpen;

    private CommandStaff commandStaff;

    @BeforeEach
    void setUp() {
        commandStaff = new CommandStaff(staffInventoryOpen);
    }

    @Test
    @DisplayName("staff should open inventory when people is player and argument is provided")
    void staff_shouldOpenInventory_whenPeopleIsPlayerArgumentIsProvided() {

        when(staffInventoryOpen.openInventory(any(), anyString())).thenReturn("");

        boolean result = commandStaff.onCommand(player, null, null, new String[]{"Tony"});

        verify(staffInventoryOpen, times(1)).openInventory(any(), anyString());
        verify(player, times(1)).sendMessage(anyString());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("staff should do nothing when people isn't player")
    void staff_shouldDoNothing_whenPeopleIsNotPlayer() {

        boolean result = commandStaff.onCommand(consoleCommandSender, null, null, new String[]{"Tony"});

        verifyNoInteractions(staffInventoryOpen);
        verify(consoleCommandSender, times(1)).sendMessage("<error>Seuls les joueurs peuvent exécuter cette commande.");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("staff should do nothing when no argument given")
    void staff_shouldDoNothing_whenNoArgumentGiven() {

        boolean result = commandStaff.onCommand(player, null, null, new String[]{});

        verifyNoInteractions(staffInventoryOpen);
        verify(player, times(1)).sendMessage("<error>/staff <nom du joueur>.");
        assertThat(result).isFalse();
    }

}
