package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;

import java.util.Optional;
import java.util.UUID;

public class HomeManage {

    private final HomeRepository homeRepository;
    private final PlayerTeleport playerTeleporter;

    public HomeManage(HomeRepository homeRepository, PlayerTeleport playerTeleporter) {
        this.homeRepository = homeRepository;
        this.playerTeleporter = playerTeleporter;
    }

    public String teleportToHome(UUID playerUUID) {
        return teleportToHome(playerUUID, 1);
    }

    public String teleportToHome(UUID playerUUID, int homeNumber) {

        Optional<HomeLocation> optionalHomeLocation = homeRepository.getHome(playerUUID, homeNumber);
        String result = "<error>Votre home n°" + homeNumber + " n'est pas défini.";

        if (optionalHomeLocation.isPresent()) {
            result = "<success>Téléportation réussie.";
            playerTeleporter.teleport(playerUUID, optionalHomeLocation.get());
        }

        return result;
    }

}
