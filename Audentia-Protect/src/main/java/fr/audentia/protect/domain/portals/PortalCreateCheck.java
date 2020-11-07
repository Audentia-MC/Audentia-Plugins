package fr.audentia.protect.domain.portals;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.protect.domain.model.Location;

import java.util.UUID;

public class PortalCreateCheck {

    private final RolesRepository rolesRepository;
    private final NetherLocationRepository netherLocationRepository;

    public PortalCreateCheck(RolesRepository rolesRepository, NetherLocationRepository netherLocationRepository) {
        this.rolesRepository = rolesRepository;
        this.netherLocationRepository = netherLocationRepository;
    }

    public String canCreate(UUID playerUUID, Location location) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.number > 4) {
            return "<error>Vous ne pouvez pas créer de portail !";
        }

        Location portalLocation = netherLocationRepository.getPortalLocation();

        if (portalLocation == null) {
            return "<error>Aucune position n'est enregistrée pour le portail du nether !";
        }

        if (portalLocation.distanceSquared(location) > 100) {
            return "<error>Votre position ne correspond pas à la position enregistrée pour le portail du nether !";
        }

        return "<success>Portail du nether créé avec succès !";
    }

}
