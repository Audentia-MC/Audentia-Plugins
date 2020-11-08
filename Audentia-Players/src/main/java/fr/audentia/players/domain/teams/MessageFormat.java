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

        String color = team.color != Color.BLACK && !role.isStaff() ? ColorsUtils.fromColorToHexadecimal(team.color) : ColorsUtils.fromColorToHexadecimal(role.color);
        String name = role.isStaff() ? role.name : (team.name.length() > 10 ? team.name.substring(0, 7) + "..." : team.name);

        String format = String.format("%s[%s]", "&" + color, name);

        return format + " %s <white>: %s";
    }

}
