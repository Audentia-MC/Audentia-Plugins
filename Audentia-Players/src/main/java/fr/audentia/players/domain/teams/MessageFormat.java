package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.utils.ColorsUtils;

import java.awt.*;
import java.util.UUID;

public class MessageFormat {

    private final RolesRepository rolesRepository;
    private final TeamsManager teamsManager;

    public MessageFormat(RolesRepository rolesRepository, TeamsManager teamsManager) {
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
    }

    public String formatMessage(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);
        Team team = teamsManager.getTeam(playerUUID);

        Color color = role.color != Color.white ? role.color : team.color;

        return "&" + ColorsUtils.fromColorToHexadecimal(color) + "[" + role.name + "] %s <white>: %s";
    }

}
