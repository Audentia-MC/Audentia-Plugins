package fr.audentia.core.domain.home;

import fr.audentia.core.domain.game.PlayerMessageSender;
import fr.audentia.core.domain.model.home.Teleport;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TeleportationsManage {

    private final HomeManage homeManage;
    private final TeleportRepository teleportRepository;
    private final PlayerMessageSender playerMessageSender;

    public TeleportationsManage(HomeManage homeManage, TeleportRepository teleportRepository, PlayerMessageSender playerMessageSender) {
        this.homeManage = homeManage;
        this.teleportRepository = teleportRepository;
        this.playerMessageSender = playerMessageSender;
    }

    public void computeTeleportations() {

        for (UUID uuid : teleportRepository.getTeleportedPlayers()) {
            computePlayerTeleportation(uuid);
        }
    }

    private void computePlayerTeleportation(UUID playerUUID) {

        ZonedDateTime actualTime = ZonedDateTime.now();

        Teleport teleport = teleportRepository.getTeleport(playerUUID);
        ZonedDateTime startTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(teleport.time), ZoneId.of("Europe/Paris"));

        long timeBeforeTeleport = 5 - ChronoUnit.SECONDS.between(startTime, actualTime);

        playerMessageSender.sendMessage(playerUUID, "<success>" + timeBeforeTeleport);

        if (timeBeforeTeleport == 0) {
            String result = homeManage.teleportToHome(playerUUID);
            playerMessageSender.sendMessage(playerUUID, result);
        }

    }

    public void cancelIfRegistered(UUID playerUUID) {

        if (teleportRepository.getTeleport(playerUUID).time == -1) {
            return;
        }

        teleportRepository.removePlayer(playerUUID);
        playerMessageSender.sendMessage(playerUUID, "<error>Téléportation annulée.");
    }

}
