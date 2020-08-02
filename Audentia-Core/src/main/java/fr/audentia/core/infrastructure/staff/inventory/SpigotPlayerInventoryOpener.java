package fr.audentia.core.infrastructure.staff.inventory;

import fr.audentia.core.application.inventories.PlayerLookInventory;
import fr.audentia.core.domain.staff.inventory.PlayerInventoryOpener;

import java.util.UUID;

public class SpigotPlayerInventoryOpener implements PlayerInventoryOpener {

    @Override
    public void openInventory(UUID staffUUID, UUID targetUUID) {

        PlayerLookInventory.openInventory(staffUUID, targetUUID);

    }

}
