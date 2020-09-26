package fr.audentia.protect.main;

import fr.audentia.core.main.AudentiaCoreManagersProvider;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;
import fr.audentia.players.utils.DataBaseConfigurationLoader;
import fr.audentia.protect.domain.HouseAction;
import fr.audentia.protect.domain.HouseRepository;
import fr.audentia.protect.infrastructure.repositories.MariaDbHouseRepository;

public class AudentiaProtectManagersProvider {

    public final HouseAction houseAction;

    public AudentiaProtectManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider, AudentiaCoreManagersProvider audentiaCoreManagersProvider, String path) {

        DatabaseConnection databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        HouseRepository houseRepository = new MariaDbHouseRepository(databaseConnection);

        houseAction = new HouseAction(houseRepository, audentiaPlayersManagersProvider.rolesRepository, audentiaPlayersManagersProvider.teamsManager, audentiaCoreManagersProvider.balanceManage);
    }

}
