package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;
import fr.audentia.core.domain.model.home.Teleport;

import java.util.Optional;
import java.util.UUID;

public class HomeManage {

    private final HomeRepository homeRepository;
    private final TeleportRepository teleportRepository;
    private final PlayerTeleport playerTeleporter;

    public HomeManage(HomeRepository homeRepository, TeleportRepository teleportRepository, PlayerTeleport playerTeleporter) {
        this.homeRepository = homeRepository;
        this.teleportRepository = teleportRepository;
        this.playerTeleporter = playerTeleporter;
    }

    public String registerTeleport(UUID playerUUID, String name) {

        Optional<Home> optionalHomeLocation = homeRepository.getHome(playerUUID, name);
        String result = "<error>Votre home \"" + name + "\" n'est pas défini.";

        if (optionalHomeLocation.isPresent()) {
            result = "<success>Téléportation dans :";
            teleportRepository.addPlayer(playerUUID, optionalHomeLocation.get().name);
        }

        return result;
    }

    public String teleportToHome(UUID playerUUID) {

        Teleport teleport = teleportRepository.getTeleport(playerUUID);

        if (teleport.time == -1) {
            return "<error>Une erreur s'est produite.";
        }

        String homeName = teleport.name;
        Optional<Home> optionalHomeLocation = homeRepository.getHome(playerUUID, homeName);
        String result = "<error>Votre home \"" + homeName + "\" n'est pas défini.";

        if (optionalHomeLocation.isPresent()) {
            result = "<success>Téléportation réussie.";
            playerTeleporter.teleport(playerUUID, optionalHomeLocation.get());
        }

        teleportRepository.removePlayer(playerUUID);
        return result;
    }

}
