package fr.audentia.players.main;

import fr.audentia.players.domain.teams.DefaultTeamsManager;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.infrastructure.roles.MariaDbRolesRepository;
import fr.audentia.players.infrastructure.teams.MariaDbTeamsRepository;
import fr.audentia.players.utils.DataBaseConfigurationLoader;

public class AudentiaPlayersManagersProvider {

    public final RolesRepository ROLES_REPOSITORY;

    public final TeamsManager TEAMS_MANAGER;

    public AudentiaPlayersManagersProvider(String path) {

        DatabaseConnection databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        TeamsRepository TEAMS_REPOSITORY = new MariaDbTeamsRepository(databaseConnection);
        ROLES_REPOSITORY = new MariaDbRolesRepository(databaseConnection);

        TEAMS_MANAGER = new DefaultTeamsManager(TEAMS_REPOSITORY);
    }

}
