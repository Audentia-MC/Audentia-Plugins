package fr.audentia.core.domain.staff.inventory;

import java.util.UUID;

public interface PlayerInventoryOpener {

    void openInventory(UUID staffUUID, UUID targetUUID);

}
