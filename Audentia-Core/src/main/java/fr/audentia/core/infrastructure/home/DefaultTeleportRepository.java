package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.TeleportRepository;
import fr.audentia.core.domain.model.home.Teleport;

import java.util.*;

public class DefaultTeleportRepository implements TeleportRepository {

    private final Map<UUID, Teleport> teleportations = new HashMap<>();
    
    @Override
    public void addPlayer(UUID playerUUID, int home) {
        this.teleportations.put(playerUUID, new Teleport(System.currentTimeMillis(), home));
    }

    @Override
    public void removePlayer(UUID playerUUID) {
        this.teleportations.remove(playerUUID);
    }

    @Override
    public Teleport getTeleport(UUID playerUUID) {

        if (!this.teleportations.containsKey(playerUUID)) {
            return new Teleport(-1, 0);
        }

        return this.teleportations.get(playerUUID);
    }

    @Override
    public Set<UUID> getTeleportedPlayers() {
        return this.teleportations.keySet();
    }
    
}
