package fr.audentia.core.domain.protect;

import fr.audentia.core.domain.model.location.Location;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class CityProtect {

    private final RolesRepository rolesRepository;
    private final CityInfosRepository cityInfosRepository;

    public CityProtect(RolesRepository rolesRepository, CityInfosRepository cityInfosRepository) {
        this.rolesRepository = rolesRepository;
        this.cityInfosRepository = cityInfosRepository;
    }

    public boolean canInteract(UUID playerUUID, Location location) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.isStaff()) {
            return true;
        }

        Location cityLocation = cityInfosRepository.getCityLocation();
        double radiusSquared = Math.pow(cityInfosRepository.getCityRadius(), 2);
        double distanceSquared = location.distanceSquared2D(cityLocation);

        return distanceSquared > radiusSquared;
    }

    public boolean isInCity(Location location) {
        Location cityLocation = cityInfosRepository.getCityLocation();
        double radiusSquared = Math.pow(cityInfosRepository.getCityRadius(), 2);
        double distanceSquared = location.distanceSquared2D(cityLocation);

        return distanceSquared <= radiusSquared * 1.25;
    }
}
