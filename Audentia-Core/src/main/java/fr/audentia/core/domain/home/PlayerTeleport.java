package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;

import java.util.UUID;

public interface PlayerTeleport {

    void teleport(UUID playerUUID, HomeLocation homeLocation);

}
