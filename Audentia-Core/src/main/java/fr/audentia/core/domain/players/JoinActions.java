package fr.audentia.core.domain.players;

import fr.audentia.core.domain.game.PlayerGameModeManage;
import fr.audentia.players.domain.PlayersRepository;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class JoinActions {

    private final RolesRepository rolesRepository;
    private final PlayerGameModeManage playerGameModeManage;
    private final PlayersRepository playersRepository;

    public JoinActions(RolesRepository rolesRepository, PlayerGameModeManage playerGameModeManage, PlayersRepository playersRepository) {
        this.rolesRepository = rolesRepository;
        this.playerGameModeManage = playerGameModeManage;
        this.playersRepository = playersRepository;
    }

    public void onJoin(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.isStaff()) {
            return;
        }

        playersRepository.addPlayerIfNotRegistered(playerUUID);
        playerGameModeManage.changeGameModeToSurvival(playerUUID);
    }

}
