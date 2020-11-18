package fr.audentia.core.domain.staff.ban;

import java.util.UUID;

public interface LogsRepository {

    void ban(UUID staffUUID, UUID bannedUUID);

    void kick(UUID staffUUID, UUID kickedUUID);

}
