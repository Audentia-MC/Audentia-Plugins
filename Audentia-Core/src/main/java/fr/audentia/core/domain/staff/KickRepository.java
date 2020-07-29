package fr.audentia.core.domain.staff;

import java.util.UUID;

public interface KickRepository {

    void kick(UUID staffUUID, UUID kickedUUID);

}
