package fr.audentia.core.domain.staff;

import java.util.UUID;

public interface BanRepository {

    void ban(UUID staffUUID, UUID bannedUUID);

}
