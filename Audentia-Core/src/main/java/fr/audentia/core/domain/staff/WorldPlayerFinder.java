package fr.audentia.core.domain.staff;

import java.util.UUID;

public interface WorldPlayerFinder {

    boolean isInWorld(String playerName);

    boolean isInWorld(UUID playerUUID);

}
