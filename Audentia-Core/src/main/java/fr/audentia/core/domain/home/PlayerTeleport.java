package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;

import java.util.UUID;

public interface PlayerTeleport {

    void teleport(UUID playerUUID, Home home);

}
