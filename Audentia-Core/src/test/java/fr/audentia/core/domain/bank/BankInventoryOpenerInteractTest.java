package fr.audentia.core.domain.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankInventoryOpenerInteractTest {

    @Mock
    private InventoryUtilities inventoryUtilities;

    @Mock
    private BankManage bankManage;

    private BankInventoryInteract bankInventoryInteract;


    @BeforeEach
    void setUp() {
        bankInventoryInteract = new BankInventoryInteract(inventoryUtilities, bankManage);
    }

    @Test
    @DisplayName("interact should call bankManager when the player has the requested emerald count in his inventory")
    void interact_shouldAdd1EmeraldToTeam_whenInteractWith1AndPeopleHas1Emerald() {

        when(inventoryUtilities.hasEmeralds(any(), anyInt())).thenReturn(true);
        when(bankManage.depositEmeralds(any(), anyInt())).thenReturn("<success>Dépôt effectué.");

        String result = bankInventoryInteract.interact(UUID.randomUUID(), 1);

        verify(bankManage, times(1)).depositEmeralds(any(), eq(1));
        verify(inventoryUtilities, times(1)).removeEmeralds(any(), eq(1));
        assertThat(result).isEqualTo("<success>Dépôt effectué.");
    }

    @Test
    @DisplayName("interact shouldn't call bankManager when the player hasn't the requested emerald count in his inventory")
    void interact_shouldDoNothing_whenInteractWith64AndPeopleHasNot64Emeralds() {

        when(inventoryUtilities.hasEmeralds(any(), anyInt())).thenReturn(false);

        String result = bankInventoryInteract.interact(UUID.randomUUID(), 64);

        verify(inventoryUtilities, times(1)).hasEmeralds(any(), eq(64));
        verifyNoMoreInteractions(inventoryUtilities);
        verifyNoInteractions(bankManage);
        assertThat(result).isEqualTo("<error>Vous ne pouvez pas déposer ce nombre d'émeraudes.");
    }

}
