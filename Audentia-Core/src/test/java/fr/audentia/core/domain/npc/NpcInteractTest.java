package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankInventoryOpen;
import fr.audentia.core.domain.bank.BankNpcProvider;
import fr.audentia.core.domain.shop.ShopInventoryOpen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NpcInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    @Mock
    private BankNpcProvider bankNpcProvider;

    @Mock
    private BankInventoryOpen bankInventoryOpen;

    @Mock
    private ShopInventoryOpen shopInventoryOpen;

    private NpcInteract npcInteract;

    @BeforeEach
    void setUp() {
        npcInteract = new NpcInteract(bankNpcProvider, bankInventoryOpen, shopInventoryOpen);
    }

    @Test
    @DisplayName("interactWithNpc should open the bank inventory when the player interacts with bank npc")
    void interactWithNpc_shouldOpenBankInventory_whenNpcIsTheBankNpc() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        npcInteract.interactWithNpc(FAKE_UUID, "Tony");

        verify(bankInventoryOpen, times(1)).openInventory(FAKE_UUID);
    }

    @Test
    @DisplayName("interactWithNpc shouldn't open the bank inventory when the player doesn't interact with bank npc")
    void interactWithNpc_shouldNotOpenBankInventory_whenNpcIsNotTheBankNpc() {

        when(bankNpcProvider.getName()).thenReturn(Optional.of("Tony"));

        npcInteract.interactWithNpc(FAKE_UUID, "Manu");

        verifyNoInteractions(bankInventoryOpen);
    }

}
