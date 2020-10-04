package fr.audentia.protect.infrastructure.repositories;

import fr.audentia.protect.domain.BuyHouseClicksRepository;
import fr.audentia.protect.domain.model.HouseClick;
import fr.audentia.protect.domain.model.Location;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
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
        this.clicks.put(playerUUID, new HouseClick(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), location));
    }

    @Override
    public void remove(UUID playerUUID) {
        this.clicks.remove(playerUUID);
    }

    @Override
    public void clearOld() {

        this.clicks.entrySet().stream()
                .filter(entry -> LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - entry.getValue().time > 8)
                .map(Map.Entry::getKey)
                .forEach(this::remove);
    }

}
