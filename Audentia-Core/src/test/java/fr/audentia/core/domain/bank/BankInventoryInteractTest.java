package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class BankInventoryInteractTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private InventoryChecker inventoryChecker;
    private BankInventoryInteract bankInventoryInteract;
    private BalanceManage balanceManage;

    @Before
    public void setUp() {
        inventoryChecker = Mockito.mock(InventoryChecker.class);
        balanceManage = Mockito.mock(BalanceManage.class);
        bankInventoryInteract = new BankInventoryInteract(inventoryChecker, balanceManage);
    }

    @Test
    public void interact_shouldAdd1EmeraldToTeam_whenInteractWith1AndPeopleHas1Emerald() {

        when(inventoryChecker.hasEmeralds(FAKE_UUID, 1)).thenReturn(true);

        bankInventoryInteract.interact(FAKE_UUID, 1);

        verify(balanceManage, times(1)).addToBalance(FAKE_UUID, 1);
    }


    @Test
    public void interact_shouldDoNothing_whenInteractWith64AndPeopleHasNot64Emeralds() {

        when(inventoryChecker.hasEmeralds(FAKE_UUID, 64)).thenReturn(false);

        bankInventoryInteract.interact(FAKE_UUID, 64);

        verifyNoInteractions(balanceManage);
    }

}
