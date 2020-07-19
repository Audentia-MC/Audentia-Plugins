package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.Role;

import java.util.UUID;

public interface RolesRepository {

    Role getRole(UUID playerUUID);

}
