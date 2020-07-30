package fr.audentia.core.domain.staff.kick;

import java.util.UUID;

public interface KickRepository {

    void kick(UUID staffUUID, UUID kickedUUID);

}
