package fr.audentia.core.domain.bank;

import java.util.UUID;

public interface InventoryUtilities {

    boolean hasEmeralds(UUID playerUUID, int count);

    void removeEmeralds(UUID playerUUID, int count);

    void addItem(UUID playerUUID, String materialName, int count);

    boolean hasItem(UUID playerUUID, String materialName, int count);

    void removeItems(UUID playerUUID, String materialName, int count);

    void clearInventory(UUID playerUUID);

}
