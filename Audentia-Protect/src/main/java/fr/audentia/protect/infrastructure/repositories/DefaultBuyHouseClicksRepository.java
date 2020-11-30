package fr.audentia.protect.infrastructure.repositories;

import fr.audentia.protect.domain.house.BuyHouseClicksRepository;
import fr.audentia.protect.domain.model.HouseClick;
import fr.audentia.protect.domain.model.Location;

import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class DefaultBuyHouseClicksRepository implements BuyHouseClicksRepository {

    private final Map<UUID, HouseClick> clicks = new HashMap<>();

    @Override
    public boolean isRegistered(UUID playerUUID, Location location) {

        if (!this.clicks.containsKey(playerUUID)) {
            return false;
        }

        return this.clicks.get(playerUUID).location.equals(location);
    }

    @Override
    public void register(UUID playerUUID, Location location) {
        this.clicks.put(playerUUID, new HouseClick(ZonedDateTime.now().toEpochSecond(), location));
    }

    @Override
    public void remove(UUID playerUUID) {
        this.clicks.remove(playerUUID);
    }

    @Override
    public void clearOld() {

        for (Map.Entry<UUID, HouseClick> entry : new HashSet<>(this.clicks.entrySet())) {
            if (ZonedDateTime.now().toEpochSecond() - entry.getValue().time > 8) {
                UUID key = entry.getKey();
                remove(key);
            }
        }

    }

}
