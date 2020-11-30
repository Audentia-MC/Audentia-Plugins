package fr.audentia.core.domain.home;

import fr.audentia.core.domain.game.PlayerMessageSender;
import fr.audentia.core.domain.model.home.Home;
import fr.audentia.core.domain.model.home.Teleport;
import fr.audentia.core.domain.model.location.Location;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TeleportationsManage {

    private final TeleportRepository teleportRepository;
    private final PlayerMessageSender playerMessageSender;
    private final PlayerTeleport playerTeleporter;

    public TeleportationsManage(TeleportRepository teleportRepository, PlayerMessageSender playerMessageSender, PlayerTeleport playerTeleporter) {
        this.teleportRepository = teleportRepository;
        this.playerMessageSender = playerMessageSender;
        this.playerTeleporter = playerTeleporter;
    }

    public void computeTeleportations() {

        for (UUID uuid : teleportRepository.getTeleportedPlayers()) {
            computePlayerTeleportation(uuid);
        }
    }

    private void computePlayerTeleportation(UUID playerUUID) {

        Teleport teleport = teleportRepository.getTeleport(playerUUID);

        if (teleport.time == -1) {
            return;
        }

        ZonedDateTime startTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(teleport.time), ZoneId.of("Europe/Paris"));

        ZonedDateTime actualTime = ZonedDateTime.now();
        long timeBeforeTeleport = 10 - ChronoUnit.SECONDS.between(startTime, actualTime);

        playerMessageSender.sendMessage(playerUUID, "<success>" + timeBeforeTeleport);

        if (timeBeforeTeleport == 0) {
            String result = teleport(playerUUID);
            playerMessageSender.sendMessage(playerUUID, result);
        }

    }

    private String teleport(UUID playerUUID) {

        Teleport teleport = teleportRepository.getTeleport(playerUUID);

        if (teleport.time == -1) {
            return "<error>Une erreur s'est produite.";
        }

        Location location = teleport.location;

        playerTeleporter.teleport(playerUUID, new Home("", location.x, location.y, location.z));
        teleportRepository.removePlayer(playerUUID);
        return "<success>Téléportation réussie.";
    }

    public void cancelIfRegistered(UUID playerUUID) {

        if (teleportRepository.getTeleport(playerUUID).time == -1) {
            return;
        }

        teleportRepository.removePlayer(playerUUID);
        playerMessageSender.sendMessage(playerUUID, "<error>Téléportation annulée.");
    }

}
