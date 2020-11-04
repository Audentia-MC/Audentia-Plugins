package fr.audentia.core.domain.game;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class GameStateManage {

    private final GamesInfosRepository gamesInfosRepository;
    private final RolesRepository rolesRepository;

    public GameStateManage(GamesInfosRepository gamesInfosRepository, RolesRepository rolesRepository) {
        this.gamesInfosRepository = gamesInfosRepository;
        this.rolesRepository = rolesRepository;
    }

    public boolean isPlaying() {

        GameState currentState = this.gamesInfosRepository.getGameState();

        return currentState == GameState.PLAYING;
    }

    public String pause(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.number > 1) {
            return "<error>Vous n'avez pas le pouvoir de mettre la partie en pause.";
        }

        gamesInfosRepository.setState(GameState.WAITING);
        return "<success>Partie mise en pause.";
    }

    public String resume(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.number > 1) {
            return "<error>Vous n'avez pas le pouvoir de relancer la partie.";
        }

        gamesInfosRepository.setState(GameState.PLAYING);
        return "<success>Partie relancée.";
    }

    public boolean isStaff(UUID playerUUID) {
        return rolesRepository.getRole(playerUUID).staff;
    }

}
