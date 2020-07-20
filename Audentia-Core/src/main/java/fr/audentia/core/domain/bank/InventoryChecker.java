package fr.audentia.core.domain.bank;

import java.util.UUID;

public interface InventoryChecker {

    boolean hasEmeralds(UUID playerUUID, int count);

}
