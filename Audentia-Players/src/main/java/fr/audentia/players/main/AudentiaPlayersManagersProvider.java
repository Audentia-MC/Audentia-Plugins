package fr.audentia.players.main;

import fr.audentia.players.domain.teams.DefaultTeamsManager;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.repositories.MariaDbRolesRepository;
import fr.audentia.players.infrastructure.repositories.teams.MariaDbTeamsRepository;

public class AudentiaPlayersManagersProvider {

    private final TeamsRepository TEAMS_REPOSITORY;
    private final RolesRepository ROLES_REPOSITORY;

    public final TeamsManager TEAMS_MANAGER;

    public AudentiaPlayersManagersProvider() {

        TEAMS_REPOSITORY = new MariaDbTeamsRepository();
        ROLES_REPOSITORY = new MariaDbRolesRepository();

        TEAMS_MANAGER = new DefaultTeamsManager(ROLES_REPOSITORY, TEAMS_REPOSITORY);
    }

}
