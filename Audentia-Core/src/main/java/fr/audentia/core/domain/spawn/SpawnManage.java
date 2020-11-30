package fr.audentia.core.domain.spawn;

import fr.audentia.core.domain.home.TeleportRepository;
import fr.audentia.core.domain.model.home.Teleport;
import fr.audentia.core.domain.model.location.Location;
import fr.audentia.core.domain.protect.CityInfosRepository;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SpawnManage {

    private final CityInfosRepository cityInfosRepository;
    private final TeleportRepository teleportRepository;

    public SpawnManage(CityInfosRepository cityInfosRepository, TeleportRepository teleportRepository) {
        this.cityInfosRepository = cityInfosRepository;
        this.teleportRepository = teleportRepository;
    }

    public String registerSpawnTeleportation(UUID playerUUID) {

        Location spawnLocation = cityInfosRepository.getCityLocation();

        teleportRepository.addPlayer(playerUUID, new Teleport(ZonedDateTime.now().toEpochSecond(), spawnLocation));
        return "<success>Téléportation dans :";
    }

}
