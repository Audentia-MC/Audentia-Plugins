package fr.audentia.core.domain.staff.teleport;

import java.util.UUID;

public interface PlayerTeleporter {

    void teleport(UUID playerUUID, UUID targetUUID);

}
