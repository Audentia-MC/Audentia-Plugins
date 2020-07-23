package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;

import java.util.Optional;
import java.util.UUID;

public interface HomeRepository {

    void saveHome(UUID playerUUID, int homeNumber, HomeLocation homeLocation);

    Optional<HomeLocation> getHome(UUID playerUUID, int homeNumber);

}
