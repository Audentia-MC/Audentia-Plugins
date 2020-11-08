package fr.audentia.core.domain.players;

import fr.audentia.core.domain.game.PlayerGameModeManage;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class JoinActions {

    private final RolesRepository rolesRepository;
    private final PlayerGameModeManage playerGameModeManage;

    public JoinActions(RolesRepository rolesRepository, PlayerGameModeManage playerGameModeManage) {
        this.rolesRepository = rolesRepository;
        this.playerGameModeManage = playerGameModeManage;
    }

    public boolean onJoin(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (!role.isPlayer()) {
            return false;
        }

        if (role.isStaff()) {
            return true;
        }

        playerGameModeManage.changeGameModeToSurvival(playerUUID);
        return true;
    }

}
