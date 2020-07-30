package fr.audentia.core.domain.staff;

import java.util.UUID;

public interface StaffInventoryOpener {

    void openInventory(UUID staffUUID, String targetName);

}
