package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;
import fr.audentia.core.domain.model.home.Teleport;
import fr.audentia.core.domain.model.location.Location;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

public class HomeManage {

    private final HomeRepository homeRepository;
    private final TeleportRepository teleportRepository;

    public HomeManage(HomeRepository homeRepository, TeleportRepository teleportRepository) {
        this.homeRepository = homeRepository;
        this.teleportRepository = teleportRepository;
    }

    public String registerTeleport(UUID playerUUID, String name) {

        Optional<Home> optionalHomeLocation = homeRepository.getHome(playerUUID, name);
        String result = "<error>Votre home \"" + name + "\" n'est pas défini.";

        if (optionalHomeLocation.isPresent()) {
            result = "<success>Téléportation dans :";
            Home home = optionalHomeLocation.get();
            Location location = new Location(home.x, home.y, home.z);
            teleportRepository.addPlayer(playerUUID, new Teleport(ZonedDateTime.now().toEpochSecond() - 300, location));
        }

        return result;
    }

}
