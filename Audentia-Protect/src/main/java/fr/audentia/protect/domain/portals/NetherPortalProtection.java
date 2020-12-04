package fr.audentia.protect.domain.portals;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.protect.domain.model.Location;

import java.util.UUID;

public class NetherPortalProtection {

    private final RolesRepository rolesRepository;
    private final NetherLocationRepository netherLocationRepository;

    public NetherPortalProtection(RolesRepository rolesRepository, NetherLocationRepository netherLocationRepository) {
        this.rolesRepository = rolesRepository;
        this.netherLocationRepository = netherLocationRepository;
    }

    public boolean canBreakBlock(UUID playerUUID, Location location) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.isStaff()) {
            return true;
        }

        Location portalLocationInNether = netherLocationRepository.getPortalLocationInNether();
        double distance = portalLocationInNether.distanceSquared(location);

        return distance >= 100;
    }

    public boolean canPlaceBlock(UUID playerUUID, Location location) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.isStaff()) {
            return true;
        }

        Location portalLocationInNether = netherLocationRepository.getPortalLocationInNether();
        double distance = portalLocationInNether.distanceSquared2D(location);

        return distance >= 100;
    }

    public boolean isNextPortal(Location location) {

        Location portalLocationInNether = netherLocationRepository.getPortalLocationInNether();
        double distance = portalLocationInNether.distanceSquared(location);

        return distance >= 100;
    }

}
