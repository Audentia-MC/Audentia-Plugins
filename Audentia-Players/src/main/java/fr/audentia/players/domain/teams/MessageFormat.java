package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.utils.ColorsUtils;

import java.awt.*;
import java.util.UUID;

public class MessageFormat {

    private final RolesRepository rolesRepository;
    private final DefaultTeamsManager teamsManager;

    public MessageFormat(RolesRepository rolesRepository, DefaultTeamsManager teamsManager) {
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
    }

    public String formatMessage(UUID playerUUID, String playerName, String content) {

        Role role = rolesRepository.getRole(playerUUID);
        Team team = teamsManager.getTeamOfPlayer(playerUUID);

        Color color = role.color != null ? role.color : team.color;
        String symbol = role.symbol != null ? role.symbol : "";

        return ColorsUtils.fromColorToString(color) + symbol + "<white><" + ColorsUtils.fromColorToString(color) + playerName + "<white>> : " + content;
    }

}
