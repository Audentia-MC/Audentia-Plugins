package fr.audentia.core.domain.players;

import fr.audentia.core.domain.game.PlayerGameModeManage;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class JoinGameModeManage {

    private final RolesRepository rolesRepository;
    private final PlayerGameModeManage playerGameModeManage;

    public JoinGameModeManage(RolesRepository rolesRepository, PlayerGameModeManage playerGameModeManage) {
        this.rolesRepository = rolesRepository;
        this.playerGameModeManage = playerGameModeManage;
    }

    public void onJoin(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.staff) {
            return;
        }

        playerGameModeManage.changeGameModeToSurvival(playerUUID);
    }

}
