package fr.audentia.players.domain.teams;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class DefaultTeamsManager implements TeamsManager {

    private final RolesRepository rolesRepository;
    private final TeamsRepository teamsRepository;

    public DefaultTeamsManager(RolesRepository rolesRepository, TeamsRepository teamsRepository) {
        this.rolesRepository = rolesRepository;
        this.teamsRepository = teamsRepository;
    }

    @Override
    public Team getTeamOfPlayer(UUID playerUUID) {

        Team defaultTeam = new Team(new Color(0, 0, 0));
        Optional<Team> optionalTeam = this.teamsRepository.getTeamOfPlayer(playerUUID);

        return optionalTeam.orElse(defaultTeam);
    }

    @Override
    public boolean isStaff(UUID playerUUID) {
        return rolesRepository.getRole(playerUUID).staff;
    }

    @Override
    public boolean isPlayer(UUID playerUUID) {
        return rolesRepository.getRole(playerUUID).player;
    }

}
