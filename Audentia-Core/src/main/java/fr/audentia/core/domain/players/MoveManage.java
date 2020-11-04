package fr.audentia.core.domain.players;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GameStateManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.awt.*;
import java.util.UUID;

public class MoveManage {

    private final RolesRepository rolesRepository;
    private final TeamsManager teamsManager;
    private final GameStateManage gameStateManage;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;

    public MoveManage(RolesRepository rolesRepository, TeamsManager teamsManager, GameStateManage gameStateManage, GamesInfosRepository gamesInfosRepository, TimeProvider timeProvider) {
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
        this.gameStateManage = gameStateManage;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
    }

    public boolean canMove(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.staff) {
            return true;
        }

        if (!role.player) {
            return false;
        }

        Team team = teamsManager.getTeam(playerUUID);

        if (!gameStateManage.isPlaying() || team.color == Color.BLACK) {
            return false;
        }

        long passedTime = timeProvider.getActualTimeInSeconds() - gamesInfosRepository.getStartTimeInSeconds();
        return gameStateManage.isPlaying() && (role.number < 8 || passedTime > 15 * 60);
    }

}
