package fr.audentia.players.main;

import fr.audentia.players.domain.teams.*;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.infrastructure.roles.MariaDbRolesRepository;
import fr.audentia.players.infrastructure.teams.MariaDbTeamsRepository;
import fr.audentia.players.utils.DataBaseConfigurationLoader;

public class AudentiaPlayersManagersProvider {

    public final RolesRepository rolesRepository;
    public final TeamsManager teamsManager;

    public final MessageFormat messageFormat;

    public AudentiaPlayersManagersProvider(String path) {

        DatabaseConnection databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        TeamsRepository TEAMS_REPOSITORY = new MariaDbTeamsRepository(databaseConnection);

        this.rolesRepository = new MariaDbRolesRepository(databaseConnection);
        this.teamsManager = new DefaultTeamsManager(TEAMS_REPOSITORY);
        this.messageFormat = new MessageFormat(rolesRepository, (DefaultTeamsManager) teamsManager);
    }

}
