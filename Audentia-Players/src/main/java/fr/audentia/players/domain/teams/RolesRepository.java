package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.roles.Role;

import java.util.UUID;

public interface RolesRepository {

    Role getRole(UUID playerUUID);

}
