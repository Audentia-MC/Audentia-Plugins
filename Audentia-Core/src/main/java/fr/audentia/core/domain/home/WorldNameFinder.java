package fr.audentia.core.domain.home;

import java.util.UUID;

public interface WorldNameFinder {

    String getWorldName(UUID playerUUID);

}
