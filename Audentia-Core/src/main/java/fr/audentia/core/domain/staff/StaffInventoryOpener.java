package fr.audentia.core.domain.staff;

import fr.audentia.players.domain.model.Role;

import java.util.UUID;

public interface StaffInventoryOpener {

    void openInventory(UUID playerUUID, String targetName);

}
