package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class BankInventoryInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private InventoryUtilities inventoryUtilities;
    private BankInventoryInteract bankInventoryInteract;
    private BalanceManage balanceManage;

    @Before
    public void setUp() {
        inventoryUtilities = Mockito.mock(InventoryUtilities.class);
        balanceManage = Mockito.mock(BalanceManage.class);
        bankInventoryInteract = new BankInventoryInteract(inventoryUtilities, balanceManage);
    }

    @Test
    public void interact_shouldAdd1EmeraldToTeam_whenInteractWith1AndPeopleHas1Emerald() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 1)).thenReturn(true);

        bankInventoryInteract.interact(FAKE_UUID, 1);

        verify(balanceManage, times(1)).addToBalance(FAKE_UUID, 1);
        verify(inventoryUtilities, times(1)).removeEmeralds(FAKE_UUID, 1);
    }

    @Test
    public void interact_shouldDoNothing_whenInteractWith64AndPeopleHasNot64Emeralds() {

        when(inventoryUtilities.hasEmeralds(FAKE_UUID, 64)).thenReturn(false);

        bankInventoryInteract.interact(FAKE_UUID, 64);

        verify(inventoryUtilities, times(1)).hasEmeralds(FAKE_UUID, 64);
        verifyNoMoreInteractions(inventoryUtilities);
        verifyNoInteractions(balanceManage);
    }

}
