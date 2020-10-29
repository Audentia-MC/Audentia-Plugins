package fr.audentia.protect.domain.house;

import fr.audentia.protect.domain.model.Location;

import java.util.UUID;

public interface BuyHouseClicksRepository {

    boolean isRegistered(UUID playerUUID, Location location);

    void register(UUID playerUUID, Location location);

    void remove(UUID playerUUID);

    void clearOld();

}
