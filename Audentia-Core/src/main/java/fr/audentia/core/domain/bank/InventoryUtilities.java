package fr.audentia.core.domain.bank;

import java.util.UUID;

public interface InventoryUtilities {

    boolean hasEmeralds(UUID playerUUID, int count);

    void removeEmeralds(UUID playerUUID, int count);

}
