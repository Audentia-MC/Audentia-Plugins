package fr.audentia.core.domain.players;

import fr.audentia.core.domain.game.GameStateManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class MoveManage {

    private final RolesRepository rolesRepository;
    private final GameStateManage gameStateManage;
    private final GamesInfosRepository gamesInfosRepository;

    public MoveManage(RolesRepository rolesRepository, GameStateManage gameStateManage, GamesInfosRepository gamesInfosRepository) {
        this.rolesRepository = rolesRepository;
        this.gameStateManage = gameStateManage;
        this.gamesInfosRepository = gamesInfosRepository;
    }

    public boolean canMove(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.staff) {
            return true;
        }

        if (!gameStateManage.isPlaying()) {
            return false;
        }

        return gameStateManage.isPlaying() || role.number <= 6 || gamesInfosRepository.getStartTimeInSeconds() > 15 * 60;
    }

}
