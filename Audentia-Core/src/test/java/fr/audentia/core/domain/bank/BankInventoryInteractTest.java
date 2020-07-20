package fr.audentia.core.domain.bank;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class BankInventoryInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private InventoryUtilities inventoryUtilities;
    private BankInventoryInteract bankInventoryInteract;
    private BankManager bankManager;

    @Before
    public void setUp() {
        inventoryUtilities = Mockito.mock(InventoryUtilities.class);
        bankManager = Mockito.mock(BankManager.class);
        bankInventoryInteract = new BankInventoryInteract(inventoryUtilities, bankManager);
    }

    @Test
    public void interact_shouldAdd1EmeraldToTeam_whenInteractWith1AndPeopleHas1EmeraldAndDayIs1() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 1)).thenReturn(true);
        when(bankManager.depositEmeralds(FAKE_UUID, 1)).thenReturn("<success>");

        bankInventoryInteract.interact(FAKE_UUID, 1);

        verify(bankManager, times(1)).depositEmeralds(FAKE_UUID, 1);
        verify(inventoryUtilities, times(1)).removeEmeralds(FAKE_UUID, 1);
    }

    @Test
    public void interact_shouldDoNothing_whenInteractWith64AndPeopleHasNot64Emeralds() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 64)).thenReturn(false);

        bankInventoryInteract.interact(FAKE_UUID, 64);

        verify(inventoryUtilities, times(1)).hasEmeralds(FAKE_UUID, 64);
        verifyNoMoreInteractions(inventoryUtilities);
        verifyNoInteractions(bankManager);
    }

}
