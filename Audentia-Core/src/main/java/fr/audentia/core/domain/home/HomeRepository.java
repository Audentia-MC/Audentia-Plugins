package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HomeRepository {

    void saveHome(UUID playerUUID, Home home);

    Optional<Home> getHome(UUID playerUUID, String name);

    List<Home> getHomes(UUID playerUUID);

    void deleteHome(UUID playerUUID, String name);

}
