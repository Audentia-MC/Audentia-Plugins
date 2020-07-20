package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankInventoryOpen;
import fr.audentia.core.domain.bank.BankNpcProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class NpcInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private BankNpcProvider bankNpcProvider;
    private BankInventoryOpen bankInventoryOpen;
    private NpcInteract npcInteract;

    @Before
    public void setUp() {
        bankNpcProvider = Mockito.mock(BankNpcProvider.class);
        bankInventoryOpen = Mockito.mock(BankInventoryOpen.class);
        npcInteract = new NpcInteract(bankNpcProvider, bankInventoryOpen);
    }

    @Test
    public void interactWithNpc_shouldOpenBankInventory_whenNpcIsTheBankNpc() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        npcInteract.interactWithNpc(FAKE_UUID, "Tony");

        verify(bankInventoryOpen, times(1)).openInventory(FAKE_UUID);
    }

    @Test
    public void interactWithNpc_shouldNotOpenBankInventory_whenNpcIsNotTheBankNpc() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        npcInteract.interactWithNpc(FAKE_UUID, "Manu");

        verifyNoInteractions(bankInventoryOpen);
    }

    // TODO : add tests for interactions with an other npc

}
