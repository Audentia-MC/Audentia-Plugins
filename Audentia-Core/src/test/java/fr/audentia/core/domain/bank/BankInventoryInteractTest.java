package fr.audentia.core.domain.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankInventoryInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    @Mock
    private InventoryUtilities inventoryUtilities;

    @Mock
    private BankManager bankManager;

    private BankInventoryInteract bankInventoryInteract;


    @BeforeEach
    void setUp() {
        bankInventoryInteract = new BankInventoryInteract(inventoryUtilities, bankManager);
    }

    @Test
    @DisplayName("interact should call bankManager when the player has the requested emerald count in his inventory")
    void interact_shouldAdd1EmeraldToTeam_whenInteractWith1AndPeopleHas1Emerald() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 1)).thenReturn(true);
        when(bankManager.depositEmeralds(FAKE_UUID, 1)).thenReturn("<success>");

        bankInventoryInteract.interact(FAKE_UUID, 1);

        verify(bankManager, times(1)).depositEmeralds(FAKE_UUID, 1);
        verify(inventoryUtilities, times(1)).removeEmeralds(FAKE_UUID, 1);
    }

    @Test
    @DisplayName("interact shouldn't call bankManager when the player hasn't the requested emerald count in his inventory")
    void interact_shouldDoNothing_whenInteractWith64AndPeopleHasNot64Emeralds() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 64)).thenReturn(false);

        bankInventoryInteract.interact(FAKE_UUID, 64);

        verify(inventoryUtilities, times(1)).hasEmeralds(FAKE_UUID, 64);
        verifyNoMoreInteractions(inventoryUtilities);
        verifyNoInteractions(bankManager);
    }

}
