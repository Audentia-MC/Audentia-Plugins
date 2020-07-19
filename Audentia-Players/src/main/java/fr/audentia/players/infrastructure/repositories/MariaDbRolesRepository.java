package fr.audentia.players.infrastructure.repositories;

import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class MariaDbRolesRepository implements RolesRepository {

    @Override
    public Role getRole(UUID playerUUID) {
        return new Role(false, false);
    }

}
