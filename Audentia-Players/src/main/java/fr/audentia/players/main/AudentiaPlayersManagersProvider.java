package fr.audentia.players.main;

import fr.audentia.players.domain.tablist.TabListProvider;
import fr.audentia.players.domain.teams.*;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.infrastructure.roles.MariaDbRolesRepository;
import fr.audentia.players.infrastructure.teams.MariaDbTeamsRepository;
import fr.audentia.players.utils.DataBaseConfigurationLoader;

public class AudentiaPlayersManagersProvider {

    public final RolesRepository rolesRepository;
    public final TeamsManager teamsManager;

    public final MessageFormat messageFormat;
    public final TabListProvider tabListProvider;
    public final DatabaseConnection databaseConnection;

    public AudentiaPlayersManagersProvider(String path) {

        this.databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        TeamsRepository teamsRepository = new MariaDbTeamsRepository(databaseConnection);

        this.rolesRepository = new MariaDbRolesRepository(databaseConnection);
        this.teamsManager = new DefaultTeamsManager(rolesRepository, teamsRepository);
        this.messageFormat = new MessageFormat(rolesRepository, teamsManager);
        this.tabListProvider = new TabListProvider(rolesRepository, teamsManager);
    }

}
