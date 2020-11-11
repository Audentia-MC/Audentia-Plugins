package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Teleport;

import java.util.Set;
import java.util.UUID;

public interface TeleportRepository {

    void addPlayer(UUID playerUUID, String name);

    void removePlayer(UUID playerUUID);

    Teleport getTeleport(UUID playerUUID);

    Set<UUID> getTeleportedPlayers();

}
